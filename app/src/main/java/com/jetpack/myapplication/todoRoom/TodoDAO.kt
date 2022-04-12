package com.jetpack.myapplication.todoRoom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDAO {

    @Query("SELECT * FROM todoTBL ORDER BY id DESC")
    fun getTodos(): LiveData<MutableList<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)

    @Update()
    suspend fun update(todo: Todo)

    @Query("UPDATE todoTBL SET notes = :notes WHERE id = :id")
    suspend fun updateData(id: Long, notes: String)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("DELETE FROM todoTBL")
    suspend fun clear()
}
