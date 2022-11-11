package com.example.storyapp.view.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.UserPreference
import kotlinx.coroutines.launch

class WelcomeViewModel(private val pref: UserPreference): ViewModel() {
    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}