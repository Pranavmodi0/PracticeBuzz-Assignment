package com.only.todolist.di

import android.app.Application
import androidx.room.Room
import com.only.todolist.data.datasource.MyDatabase
import com.only.todolist.data.repository.RepositoryImpl
import com.only.todolist.data.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideMyDataBase(app: Application): MyDatabase {
        return Room.databaseBuilder(
            app,
            MyDatabase::class.java,
            "MyDataBase"
        )
            .addMigrations()
            .build()
    }

    @Provides
    @Singleton
    fun provideMyDao(myDatabase: MyDatabase): TodoRepository {
        return RepositoryImpl(myDatabase.dao())
    }
}