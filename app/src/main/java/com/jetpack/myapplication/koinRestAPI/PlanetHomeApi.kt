package com.jetpack.myapplication.koinRestAPI

import com.jetpack.myapplication.koinRestAPI.db.model.PlanetHome
import retrofit2.Response
import retrofit2.http.GET

interface PlanetHomeApi {
    @GET("/api/v1/planets/")
    suspend fun getAllPlanets(): Response<List<PlanetHome>>
}