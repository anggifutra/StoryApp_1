package com.example.storyapp.view.login

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.EmailCheck
import com.example.storyapp.PasswordCheck
import com.example.storyapp.R
import com.example.storyapp.data.UserLogin
import com.example.storyapp.data.UserPreference
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.view.ViewModelFactory
import com.example.storyapp.view.authentic.AuthenticActivity
import com.example.storyapp.view.list_story.ListStoryActivity
import com.example.storyapp.view.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {

    private lateinit var myEmailCheck: EmailCheck
    private lateinit var myPasswordCheck: PasswordCheck
    private var _binding: ActivityLoginBinding? = null
    private val binding  get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.title = "Login"
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myEmailCheck = findViewById(R.id.ed_login_email)
        myPasswordCheck = findViewById(R.id.ed_login_password)

        setupView()

        viewModel = ViewModelProvider(this,ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.btnLogin.setOnClickListener {
            viewModel.login(myEmailCheck.text.toString(),myPasswordCheck.text.toString())
            viewModel.loginData.observe(this){
                if (it != null){
                    viewModel.saveUser(
                        UserLogin(it.loginResult?.name.toString(),
                        it.loginResult?.token.toString(), isLogin = true)
                    )
                    val intent = Intent(this@LoginActivity, ListStoryActivity::class.java)
                    intent.putExtra(ListStoryActivity.TOKEN, it.loginResult?.token.toString())
                    startActivity(intent)
                    finish()
                }
            }
            viewModel.errorMessage.observe(this){
                if (!it.equals("")){
                    showToast("Login Failed : $it")
                }
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val TAG = "LoginViewModel"
    }
}