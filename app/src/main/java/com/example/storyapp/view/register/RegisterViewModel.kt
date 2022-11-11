package com.example.storyapp.view.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.data.ApiConfig
import com.example.storyapp.data.Register
import com.example.storyapp.view.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {

    val registerData = MutableLiveData<Register>()
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(name: String,email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().addUserRegister(name,email,password)
        client.enqueue(object : Callback<Register> {
            override fun onResponse(
                call: Call<Register>,
                response: Response<Register>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        registerData.value = response.body()
                        _errorMessage.value = ""
                    }
                }else{
                    _errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<Register>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message.toString()
                Log.e(LoginActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }
}