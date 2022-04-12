package com.jetpack.myapplication.koinRestAPI.repository

import androidx.lifecycle.LiveData
import com.jetpack.myapplication.koinRestAPI.db.model.PlanetHome
/*import com.jetpack.myapplication.koinRestAPI.util.AppResult*/
import retrofit2.Response

interface PlanetHomeRepository {
    suspend fun getAllPlanets(): Response<List<PlanetHome>>
    // suspend fun getAllPlanets(): AppResult<List<PlanetHome>>
}
