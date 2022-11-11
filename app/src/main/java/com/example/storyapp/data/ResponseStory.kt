package com.example.storyapp.data

import com.google.gson.annotations.SerializedName

data class ResponseStory(
    @field:SerializedName("listStory")
    val listStory: List<ListStory>
)

data class ListStory(
    @field:SerializedName("id")
    var id: String?,

    @field:SerializedName("name")
    var name: String?,

    @field:SerializedName("description")
    var description: String?,

    @field:SerializedName("photoUrl")
    var photoUrl: String?,

    @field:SerializedName("lat")
    val lat: Double? = null,

    @field:SerializedName("lon")
    val lon: Double? = null
)

data class FileUploadResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
