package com.example.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.api.NetworkResponse
import com.example.api.api.RetrofitInstance
import com.example.api.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: MutableLiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(Constant.apiKey, city)
                if (response.isSuccessful) {
//            Log.i("Response : ",response.body().toString())
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
//            Log.i("Error : ", response.message())
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
            }
        }

    }
}