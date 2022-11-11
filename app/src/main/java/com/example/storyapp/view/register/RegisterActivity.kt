package com.example.storyapp.view.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.EmailCheck
import com.example.storyapp.PasswordCheck
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var myEmailCheck: EmailCheck
    private lateinit var myPasswordCheck: PasswordCheck
    private var _binding: ActivityRegisterBinding? = null
    private val binding  get() = _binding!!
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.title = "Register"
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myEmailCheck = findViewById(R.id.ed_register_email)
        myPasswordCheck = findViewById(R.id.ed_register_password)
        val name: TextView = findViewById(R.id.ed_add_description)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding.btnRegister.setOnClickListener {
            viewModel.isLoading.observe(this){
                showLoading(it)
            }
            viewModel.register(name.text.toString(),myEmailCheck.text.toString(),myPasswordCheck.text.toString())
            viewModel.registerData.observe(this){
                if (it != null){
                    showToast("Register Success")
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
            viewModel.errorMessage.observe(this){
                if (!it.equals("")){
                    showToast("Register Failed : $it")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}