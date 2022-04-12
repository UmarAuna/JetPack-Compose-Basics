package com.jetpack.myapplication.koinRestAPI.util

import com.jetpack.myapplication.koinRestAPI.db.model.PlanetHome

/**
 * AppResult class is a wrapper class that helps to handle success and failure scenarios with co routines
 */
/*sealed class AppResult<out T> : List<PlanetHome> {

    data class Success<out T>(val successData: T) : AppResult<T>()
    class Error(
        private val exception: Exception,
        val message: String? = exception.message
    ) : AppResult<Nothing>()
}*/
