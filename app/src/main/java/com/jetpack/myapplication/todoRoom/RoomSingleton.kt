package com.jetpack.myapplication.todoRoom

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Todo::class],
    version = 4,
    exportSchema = false,
)
abstract class RoomSingleton : RoomDatabase() {

    abstract fun todoDao(): TodoDAO

    companion object {
        private var INSTANCE: RoomSingleton? = null
        fun getInstance(context: Context): RoomSingleton {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    RoomSingleton::class.java,
                    "roomdb"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as RoomSingleton
        }
    }
}
