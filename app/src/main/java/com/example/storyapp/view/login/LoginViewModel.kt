package com.example.storyapp.view.login

import android.util.Log
import androidx.lifecycle.*
import com.example.storyapp.data.ApiConfig
import com.example.storyapp.data.ResponseUserLogin
import com.example.storyapp.data.UserLogin
import com.example.storyapp.data.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref:UserPreference): ViewModel() {

    val loginData = MutableLiveData<ResponseUserLogin>()
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveUser(user: UserLogin) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().addUserLogin(email,password)
        client.enqueue(object : Callback<ResponseUserLogin> {
            override fun onResponse(
                call: Call<ResponseUserLogin>,
                response: Response<ResponseUserLogin>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.loginResult != null) {
                            loginData.value = response.body()
                            _errorMessage.value = ""
                        }
                    }
                }else{
                    _errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseUserLogin>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message.toString()
                Log.e(LoginActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }
}