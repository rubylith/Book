package com.fyl.book.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun getUsers() = userDao.getUsers()
    fun getUser(id: Long) = userDao.getUser(id)
    suspend fun insertAll(users: List<User>) = userDao.insertAll(users)
    suspend fun insert(user: User) = userDao.insert(user)
    suspend fun update(user: User) = userDao.update(user)
    suspend fun delete(user: User) = userDao.delete(user)
}

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY id")
    fun getUsers(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Long): Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Insert
    suspend fun insert(user: User)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var name: String,
    var password:String
)