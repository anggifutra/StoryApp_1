package com.example.storyapp.view.list_story

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.data.UserPreference
import com.example.storyapp.databinding.ActivityDetailBinding
import com.example.storyapp.view.ViewModelFactory
import com.squareup.picasso.Picasso

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.title = "Detail User"
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this
            , ViewModelFactory(UserPreference.getInstance(dataStore))
        )[DetailViewModel::class.java]
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        val id = intent.getStringExtra(EXTRA_ID)
        viewModel.getUser().observe(this){
            viewModel.findStory(it.token,id.toString())
        }
        viewModel.detailStory.observe(this){
            if (it != null) {
                binding.apply {
                    tvDetailName.text = it.story?.name.toString()
                    tvDetailDescription.text = it.story?.description.toString()
                    Picasso.get()
                        .load(it.story?.photoUrl.toString())
                        .into(ivDetailPhoto)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val EXTRA_ID = "extra_id"
    }
}