package com.only.todolist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.only.todolist.data.entity.TodoEntity
import com.only.todolist.domain.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : ViewModel(){

    private val _todoDetailList = MutableStateFlow(emptyList<TodoEntity>())
    val todoDetailList = _todoDetailList.asStateFlow()

    private val _submissionStatus = MutableStateFlow<Boolean?>(null)
    val submissionStatus = _submissionStatus.asStateFlow()

    init {
        getTodoDetails()
    }

    fun getTodoDetails(){
        viewModelScope.launch(IO) {
            userUseCase().collectLatest {
                _todoDetailList.tryEmit(it)
            }
        }
    }

    fun insertTodo(todoEntity: TodoEntity){
        viewModelScope.launch(IO) {
            try {
                userUseCase(todoEntity)
                _submissionStatus.emit(true)
                clearFields()
            } catch (e: Exception) {
                _submissionStatus.emit(false)
            }
        }
    }

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()
    fun setTitle(name:String){
        _title.tryEmit(name)
    }

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()
    fun setDescription(age:String){
        _description.tryEmit(age)
    }


    private fun clearFields() {
        _title.tryEmit("")
        _description.tryEmit("")
    }
}