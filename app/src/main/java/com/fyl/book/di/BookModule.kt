package com.fyl.book.di

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import com.fyl.book.data.BookDatabase
import com.fyl.book.data.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class BookModule {

    @Singleton
    @Provides
    fun provideEdgeBarDatabase(@ApplicationContext context: Context): BookDatabase {
        return BookDatabase.getInstance(context)
    }

    @Provides
    fun provideUserDao(edgeBarDatabase: BookDatabase): UserDao {
        return edgeBarDatabase.userDao()
    }

    @Provides
    fun providePackageManager(@ApplicationContext context: Context): PackageManager {
        return context.packageManager
    }

    @Provides
    fun provideResources(@ApplicationContext context: Context): Resources {
        return context.resources
    }
}
