package com.only.todolist.domain

import com.only.todolist.data.entity.TodoEntity
import com.only.todolist.data.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todoEntity: TodoEntity) {
        repository.insert(todoEntity)
    }

    suspend operator fun invoke(): Flow<List<TodoEntity>> {
        return repository.getList()
    }
}