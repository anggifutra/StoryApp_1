package com.example.storyapp.view.upload

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.storyapp.data.ApiConfig
import com.example.storyapp.data.FileUploadResponse
import com.example.storyapp.data.UserLogin
import com.example.storyapp.data.UserPreference
import com.example.storyapp.view.login.LoginActivity
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadViewModel(private val pref: UserPreference): ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserLogin> {
        return pref.getUser().asLiveData()
    }

    fun upload(token: String, file: MultipartBody.Part, description: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().uploadImage("Bearer $token",file,description)
        client.enqueue(object : Callback<FileUploadResponse> {
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _errorMessage.value = responseBody.message
                    }
                }else{
                    _errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message.toString()
                Log.e(LoginActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }
}