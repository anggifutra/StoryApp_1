package com.example.storyapp.view.authentic

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.MainActivity
import com.example.storyapp.R
import com.example.storyapp.data.UserPreference
import com.example.storyapp.databinding.ActivityAuthenticBinding
import com.example.storyapp.view.ViewModelFactory
import com.example.storyapp.view.login.LoginActivity
import com.example.storyapp.view.register.RegisterActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class AuthenticActivity : AppCompatActivity() {

    private var _binding: ActivityAuthenticBinding? = null
    private val binding  get() = _binding!!
    private lateinit var viewModel: AuthenticViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.title = "Authenticated"
        _binding = ActivityAuthenticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.welcomeImg.setImageResource(R.drawable.welcome_img)

        viewModel = ViewModelProvider(
            this
            , ViewModelFactory(UserPreference.getInstance(dataStore))
        )[AuthenticViewModel::class.java]

        viewModel.getUser().observe(this){
            if (it.isLogin){
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        playAnimation()

        binding.login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.welcomeImg, View.TRANSLATION_X, -50f, 50f).apply {
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.login, View.ALPHA, 10f).setDuration(1000)
        val register = ObjectAnimator.ofFloat(binding.register, View.ALPHA, 10f).setDuration(1000)
        val title = ObjectAnimator.ofFloat(binding.welcome, View.ALPHA, 10f).setDuration(1000)


        AnimatorSet().apply {
            playSequentially(title, login,register)
            start()
        }
    }
}