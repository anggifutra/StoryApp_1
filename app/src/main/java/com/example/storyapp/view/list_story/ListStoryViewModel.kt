package com.example.storyapp.view.list_story

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.storyapp.data.*
import com.example.storyapp.view.list_story.ListStoryActivity.Companion.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListStoryViewModel(private val pref: UserPreference) : ViewModel() {

    private val _listStory = MutableLiveData<List<ListStory>>()
    val listStory: LiveData<List<ListStory>> = _listStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserLogin> {
        return pref.getUser().asLiveData()
    }

    fun getStory(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getStory("Bearer $token")
        client.enqueue(object : Callback<ResponseStory> {
            override fun onResponse(
                call: Call<ResponseStory>,
                response: Response<ResponseStory>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listStory.value = response.body()?.listStory
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseStory>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

}