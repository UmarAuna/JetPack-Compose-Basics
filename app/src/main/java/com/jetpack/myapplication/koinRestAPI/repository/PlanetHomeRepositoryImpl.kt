package com.jetpack.myapplication.koinRestAPI.repository

import android.content.Context
import android.util.Log
import com.jetpack.myapplication.koinRestAPI.PlanetHomeApi
import com.jetpack.myapplication.koinRestAPI.db.PlanetHomeDao
import com.jetpack.myapplication.koinRestAPI.db.model.PlanetHome
import com.jetpack.myapplication.koinRestAPI.util.TAG
/*import com.jetpack.myapplication.koinRestAPI.util.AppResult*/
/*import com.jetpack.myapplication.koinRestAPI.util.Utils.handleSuccess*/
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PlanetHomeRepositoryImpl(
    private val api: PlanetHomeApi,
    private val dao: PlanetHomeDao
) : PlanetHomeRepository {

    override suspend fun getAllPlanets(): Response<List<PlanetHome>> {
        /*if (NetworkManager().isOnline(context)) {*/
        /* return try {*/
        val response = api.getAllPlanets()
        Log.d(TAG, "${response.body()}")
        // save the data
        response.body()?.let {
            withContext(Dispatchers.IO) {
                dao.deleteAll()
                dao.add(it)
            }
        }
        return response
       /* } catch (e: Exception) {
            e.printStackTrace()
        }*/
        /*}*/ /*else {
            // check in db if the data exists
            val data = getCountriesDataFromCache()
            return if (data.isNotEmpty()) {
                Log.d(TAG, "from db")

                AppResult.Success(data)
            } else {
                Log.d("TAG", "No Internet from Repository 2")
                // no network
                context.noNetworkConnectivityError()
            }
        }*/
    }

 /*   override suspend fun getAllPlanets(): List<PlanetHome> {
        *//*if (NetworkManager().isOnline(context)) {*//*
        return try {
            val response = api.getAllPlanets()
            if (response.isSuccessful) {
                // save the data
                response.body()?.let {
                    withContext(Dispatchers.IO) {
                        dao.deleteAll()
                        dao.add(it)
                    }
                }
                handleSuccess(response)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            AppResult.Error(e)
        }
        *//*}*//* *//*else {
            // check in db if the data exists
            val data = getCountriesDataFromCache()
            return if (data.isNotEmpty()) {
                Log.d(TAG, "from db")

                AppResult.Success(data)
            } else {
                Log.d("TAG", "No Internet from Repository 2")
                // no network
                context.noNetworkConnectivityError()
            }
        }*//*
    }
*/
    private suspend fun getCountriesDataFromCache(): List<PlanetHome> {
        return withContext(Dispatchers.IO) {
            dao.findAll()
        }
    }
}
