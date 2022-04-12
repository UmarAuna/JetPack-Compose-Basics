package com.jetpack.myapplication.todoRoom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todoTBL")
data class Todo(
    @PrimaryKey
    val id: Long?,
    @ColumnInfo(name = "uuid")
    val fullName: String?,
    @ColumnInfo(name = "notes")
    val notes: String?
) : Serializable
