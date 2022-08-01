package com.fyl.book.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.*
import com.fyl.book.utils.LogUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val keyUserFile = "keyUserFile"
const val userFile = "user.json"

/**
 * The room database for this app
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val TAG = "BookDatabase"
        private const val DATABASE_NAME = "BookDatabase"
        @Volatile private var instance: BookDatabase? = null

        fun getInstance(context: Context): BookDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): BookDatabase {
            LogUtils.d(true, TAG, "buildDatabase()")
            return Room.databaseBuilder(context, BookDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<BookDatabaseWorker>()
                                    .setInputData(workDataOf(keyUserFile to userFile))
                                    .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }
}

class BookDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val result = runCatching {
            inputData.getString(keyUserFile)?.takeIf { it.isNotEmpty() }?.let { filename ->
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val userType = object : TypeToken<List<User>>() {}.type
                        val userList: List<User> = Gson().fromJson(jsonReader, userType)

                        val database = BookDatabase.getInstance(applicationContext)
                        database.userDao().insertAll(userList)
                    }
                }
            }
        }
        if (result.isSuccess) Result.success() else Result.failure()
    }
}
