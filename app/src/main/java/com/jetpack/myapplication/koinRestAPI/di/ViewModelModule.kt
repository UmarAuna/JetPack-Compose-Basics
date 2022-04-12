package com.jetpack.myapplication.koinRestAPI.di

import com.jetpack.myapplication.koinRestAPI.viewmodel.PlanetHomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PlanetHomeViewModel(get(), get(), get(), androidContext()) }
}
