package com.jetpack.myapplication.koinRestAPI.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jetpack.myapplication.koinRestAPI.db.model.PlanetHome

@Database(
    entities = [PlanetHome::class],
    version = 1, exportSchema = false
)

abstract class PlanetHomeDatabase : RoomDatabase() {
    abstract val planetDao: PlanetHomeDao
}