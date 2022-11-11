package com.example.storyapp.view.list_story

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.data.ListStory
import com.example.storyapp.data.UserPreference
import com.example.storyapp.databinding.ActivityListStoryBinding
import com.example.storyapp.view.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class ListStoryActivity : AppCompatActivity() {

    private var _binding: ActivityListStoryBinding? = null
    private val binding  get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.title = "Story"
        _binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val listStoryViewModel = ViewModelProvider(
            this
            ,ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ListStoryViewModel::class.java]
        listStoryViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        listStoryViewModel.getUser().observe(this){
            listStoryViewModel.getStory(it.token)
        }
        listStoryViewModel.listStory.observe(this) { items ->
            setStoryData(items as ArrayList<ListStory>)
        }

    }

    private fun setStoryData(items : ArrayList<ListStory>) {
        val adapter = StoryAdapter(items)
        adapter.setOnItemClickCallback(object: StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStory) {
                val i = Intent(this@ListStoryActivity, DetailActivity::class.java)
                i.putExtra(DetailActivity.EXTRA_ID, data.id)
                startActivity(i)
            }
        })
        binding.rvStory.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)
        binding.rvStory.setHasFixedSize(true)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val TOKEN = "token"
        const val TAG = "ListStoryActivity"
    }
}