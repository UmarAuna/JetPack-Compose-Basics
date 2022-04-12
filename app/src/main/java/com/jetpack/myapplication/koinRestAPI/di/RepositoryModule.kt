package com.jetpack.myapplication.koinRestAPI.di

import com.jetpack.myapplication.koinRestAPI.PlanetHomeApi
import com.jetpack.myapplication.koinRestAPI.db.PlanetHomeDao
import com.jetpack.myapplication.koinRestAPI.repository.PlanetHomeRepository
import com.jetpack.myapplication.koinRestAPI.repository.PlanetHomeRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    fun providePlanetHomeRepository(api: PlanetHomeApi, dao: PlanetHomeDao): PlanetHomeRepository {
        return PlanetHomeRepositoryImpl(api, dao)
    }
    single { providePlanetHomeRepository(get(), get()) }
}
