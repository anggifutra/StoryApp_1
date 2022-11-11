package com.example.storyapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.MainViewModel
import com.example.storyapp.data.UserPreference
import com.example.storyapp.view.authentic.AuthenticActivity
import com.example.storyapp.view.authentic.AuthenticViewModel
import com.example.storyapp.view.list_story.DetailViewModel
import com.example.storyapp.view.list_story.ListStoryViewModel
import com.example.storyapp.view.login.LoginViewModel
import com.example.storyapp.view.maps.MapsViewModel
import com.example.storyapp.view.upload.UploadViewModel
import com.example.storyapp.view.welcome.WelcomeViewModel

class ViewModelFactory (private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(pref) as T
            }
            modelClass.isAssignableFrom(AuthenticViewModel::class.java) -> {
                AuthenticViewModel(pref) as T
            }
            modelClass.isAssignableFrom(UploadViewModel::class.java) -> {
                UploadViewModel(pref) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ListStoryViewModel::class.java) -> {
                ListStoryViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}