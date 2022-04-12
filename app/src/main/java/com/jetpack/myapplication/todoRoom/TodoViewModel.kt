package com.jetpack.myapplication.todoRoom

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : ViewModel() {

    private val db = RoomSingleton.getInstance(application)

    internal val todoList: LiveData<MutableList<Todo>> = db.todoDao().getTodos()

    fun insert(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            db.todoDao().insert(todo)
        }
    }

    fun update(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            db.todoDao().update(todo)
        }
    }

    fun updateData(id: Long, notes: String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.todoDao().updateData(id, notes)
        }
    }

    fun delete(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            db.todoDao().delete(todo)
        }
    }

    fun clear() {
        viewModelScope.launch(Dispatchers.IO) {
            db.todoDao().clear()
        }
    }
}
