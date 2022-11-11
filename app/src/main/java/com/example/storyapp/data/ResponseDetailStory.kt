package com.example.storyapp.data

import com.google.gson.annotations.SerializedName

data class ResponseDetailStory(
    @field:SerializedName("story")
    val story: Story? = null
)

data class Story(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null
)
