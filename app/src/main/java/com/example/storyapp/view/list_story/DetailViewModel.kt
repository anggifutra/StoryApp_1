package com.example.storyapp.view.list_story

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.storyapp.data.ApiConfig
import com.example.storyapp.data.ResponseDetailStory
import com.example.storyapp.data.UserLogin
import com.example.storyapp.data.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val pref: UserPreference): ViewModel() {

    private val _detailStory = MutableLiveData<ResponseDetailStory>()
    val detailStory: LiveData<ResponseDetailStory> = _detailStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserLogin> {
        return pref.getUser().asLiveData()
    }

    fun findStory(token: String,id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailStory("Bearer $token", id)
        client.enqueue(object : Callback<ResponseDetailStory> {
            override fun onResponse(
                call: Call<ResponseDetailStory>,
                response: Response<ResponseDetailStory>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.story != null) {
                            _detailStory.value = response.body()
                        }
                    }else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseDetailStory>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        const val TAG = "DetailViewModel"
    }
}