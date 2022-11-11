package com.example.storyapp.view.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.storyapp.data.*
import com.example.storyapp.view.list_story.ListStoryActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(private val pref: UserPreference): ViewModel() {
    private val _mapsData = MutableLiveData<List<ListStory>>()
    val mapsData: LiveData<List<ListStory>> = _mapsData

    fun getUser(): LiveData<UserLogin> {
        return pref.getUser().asLiveData()
    }

    fun getMap(token: String) {
        val client = ApiConfig.getApiService().getMaps("Bearer $token")
        client.enqueue(object : Callback<ResponseStory> {
            override fun onResponse(
                call: Call<ResponseStory>,
                response: Response<ResponseStory>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _mapsData.value = response.body()?.listStory
                    }
                } else {
                    Log.e(ListStoryActivity.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseStory>, t: Throwable) {
                Log.e(ListStoryActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }
}