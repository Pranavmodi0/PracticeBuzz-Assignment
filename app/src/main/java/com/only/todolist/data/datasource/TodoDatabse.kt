package com.only.todolist.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.only.todolist.data.entity.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 1
)
abstract class MyDatabase: RoomDatabase(){
    abstract fun dao(): TodoDao
}