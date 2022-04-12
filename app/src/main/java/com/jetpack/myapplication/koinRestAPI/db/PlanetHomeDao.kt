package com.jetpack.myapplication.koinRestAPI.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jetpack.myapplication.koinRestAPI.db.model.PlanetHome

@Dao
interface PlanetHomeDao {
    @Query("SELECT * FROM PlanetHome")
    fun findAll(): List<PlanetHome>

    @Query("SELECT * FROM PlanetHome")
    fun getAllPlanets(): LiveData<List<PlanetHome>>

    @Query("DELETE FROM PlanetHome")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(users: List<PlanetHome>)
}