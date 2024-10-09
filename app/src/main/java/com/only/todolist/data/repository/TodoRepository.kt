package com.only.todolist.data.repository

import com.only.todolist.data.datasource.TodoDao
import com.only.todolist.data.entity.TodoEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TodoRepository {

    suspend fun insert(todoEntity: TodoEntity)

    suspend fun getList(): Flow<List<TodoEntity>>
}

class RepositoryImpl @Inject constructor(
    private val dao: TodoDao,
) : TodoRepository {
    override suspend fun insert(todoEntity: TodoEntity) {
        withContext(IO) {
            dao.insert(todoEntity)
        }
    }

    override suspend fun getList(): Flow<List<TodoEntity>> {
        return withContext(IO){
            dao.getAllTodo()
        }
    }
}