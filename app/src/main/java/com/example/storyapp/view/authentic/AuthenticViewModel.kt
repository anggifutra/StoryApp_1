package com.example.storyapp.view.authentic

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.storyapp.data.UserLogin
import com.example.storyapp.data.UserPreference

class AuthenticViewModel(private val pref: UserPreference):ViewModel() {
    fun getUser(): LiveData<UserLogin> {
        return pref.getUser().asLiveData()
    }
}