package com.jetpack.myapplication.koinRestAPI.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.jetpack.myapplication.koinRestAPI.db.PlanetHomeDao
import com.jetpack.myapplication.koinRestAPI.db.model.PlanetHome
import com.jetpack.myapplication.koinRestAPI.repository.PlanetHomeRepository
import com.jetpack.myapplication.koinRestAPI.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlanetHomeViewModel(
    private val repository: PlanetHomeRepository,
    private val planetHomeDao: PlanetHomeDao,
    private val networkManager: NetworkManager,
    private val context: Context,
) : ViewModel() {
    // val showLoading = ObservableBoolean()
    val planetList = MutableLiveData<List<PlanetHome>>()

    val observePlanet
        get() = Transformations.map(planetHomeDao.getAllPlanets()) {
            it
        }

   /* private val _observeError = MutableLiveData<String>()
    val observeError
        get() = _observeError*/

    val showError = SingleLiveEvent<String>()
    var errorMessage: String by mutableStateOf("")

    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val observeError
        get() = _error

    fun getAllPlanets() {
        // _isLoading.postValue(true)
        _isLoading.postValue(true)
        if (networkManager.isOnline(context)) {
            viewModelScope.launch(Dispatchers.IO) {

                try {
                    val response = repository.getAllPlanets()
                    _isLoading.postValue(false)
                    when {
                        response.isSuccessful -> {
                            Resource.success(response.body())
                        }
                        else -> {

                            errorMessage = when (response.code()) {
                                404 -> {
                                    // observeError.postValue("Not Found")
                                    "Not Found"
                                }
                                500 -> {
                                    // observeError.postValue("Server Broken")
                                    "Server Broken"
                                }
                                else -> {
                                    // observeError.postValue("Unknown Error")
                                    "Unknown Error"
                                }
                            }
                           /* Resource.error(result.errorBody().toString(), "DATA")
                            errorMessage = result.errorBody().toString()*/
                            // showError.postValue("DATA")
                            // observeError.postValue("DATA")
                        }
                    }

                  /*  when (result) {
                        is AppResult.Success -> {
                            planetList.value = result.successData
                            result.successData
                            // showError.value = "Good"
                            // errorMessage = "Good"
                        }
                        *//*is AppResult.Error -> {
                            errorMessage = result.message
                            Log.d(TAG, "No Internet from View Model")
                        }*//*
                        is AppResult.Error -> {
                            // showError.value = result.exception.message
                            errorMessage = "Internal Server Error"
                            // _error.postValue("Internal Server Error")
                        }
                    }*/
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            _isLoading.postValue(false)
            errorMessage = "No Internet"
        }
    }

    /*@SuppressLint("NullSafeMutableLiveData")
    fun getAllPlanets() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            if (NetworkManager().isOnline(context)) {
                try {
                    val result = repository.getAllPlanets()
                    _isLoading.postValue(false)
                    when (result) {
                        is AppResult.Success -> {
                            planetList.value = result.successData
                            result.successData
                            // showError.value = "Good"
                            // errorMessage = "Good"
                        }
                        *//*is AppResult.Error -> {
                            errorMessage = result.message
                            Log.d(TAG, "No Internet from View Model")
                        }*//*
                        is AppResult.Error -> {
                            // showError.value = result.exception.message
                            errorMessage = "Internal Server Error"
                            // _error.postValue("Internal Server Error")
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                _isLoading.postValue(false)
                errorMessage = "No Internet"
                // _error.postValue("No Internet")
            }
        }
    }*/
}
