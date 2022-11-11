package com.example.storyapp.view.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.MainActivity
import com.example.storyapp.R
import com.example.storyapp.data.UserPreference
import com.example.storyapp.view.ViewModelFactory
import com.example.storyapp.view.list_story.ListStoryActivity
import com.example.storyapp.view.maps.MapsActivity
import com.example.storyapp.view.upload.UploadActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class WelcomeActivity : AppCompatActivity() {

    private lateinit var welcomeViewModel: WelcomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val welcomeImg: ImageView = findViewById(R.id.welcomeImg)
        val logout: Button = findViewById(R.id.login)

        welcomeImg.setImageResource(R.drawable.welcome_img)

        welcomeViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore))
        )[WelcomeViewModel::class.java]
        logout.setOnClickListener {
            welcomeViewModel.logout()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                val i = Intent(this, ListStoryActivity::class.java)
                startActivity(i)
                finish()
                true
            }R.id.menu2 -> {
                val i = Intent(this, UploadActivity::class.java)
                startActivity(i)
                true
            }R.id.menu3 -> {
                val i = Intent(this, MapsActivity::class.java)
                startActivity(i)
                true
            }R.id.menu4 -> {
                val i = Intent(this, WelcomeActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }
}