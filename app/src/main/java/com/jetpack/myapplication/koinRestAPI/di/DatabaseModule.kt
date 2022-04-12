package com.jetpack.myapplication.koinRestAPI.di

import android.app.Application
import androidx.room.Room
import com.jetpack.myapplication.koinRestAPI.db.PlanetHomeDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): PlanetHomeDatabase {
        return Room.databaseBuilder(application, PlanetHomeDatabase::class.java, "PlanetsDb")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun providePlanetDao(database: PlanetHomeDatabase) = database.planetDao

    single { provideDatabase(androidApplication()) }
    single { providePlanetDao(get()) }
}
