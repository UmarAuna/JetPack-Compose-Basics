package com.jetpack.myapplication.koinRestAPI.di

import com.jetpack.myapplication.koinRestAPI.PlanetHomeApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun providePlanetHomeApi(retrofit: Retrofit): PlanetHomeApi {
        return retrofit.create(PlanetHomeApi::class.java)
    }

    single { providePlanetHomeApi(get()) }
}