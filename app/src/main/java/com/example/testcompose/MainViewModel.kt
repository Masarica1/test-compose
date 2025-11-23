package com.example.testcompose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.testcompose.utils.MainPageCategory
import com.example.testcompose.utils.TestTodoData
import com.example.testcompose.utils.TodoData

class MainViewModel: ViewModel() {
    private val _todoList = mutableStateListOf<TodoData>()
    private val _currentPage = mutableStateOf(MainPageCategory.Main)
    val todoList: List<TodoData> = _todoList
    val currentPage: State<MainPageCategory> = _currentPage

    init {
        _todoList.addAll(TestTodoData)
    }

    fun toggleTodo(id: String) {
        val index = _todoList.indexOfFirst { it.id == id }
        if (index != -1) _todoList[index] = _todoList[index].copy(isDone = !_todoList[index].isDone)
    }

    fun setPage(page: MainPageCategory) {
        _currentPage.value = page
    }
}