package com.example.storyapp.data

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun addUserLogin(
        @Field("email") email : String,
        @Field("password") password : String
    ): Call<ResponseUserLogin>

    @FormUrlEncoded
    @POST("register")
    fun addUserRegister(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("password") password : String
    ): Call<Register>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: String,
    ): Call<FileUploadResponse>

    @GET("stories")
    fun getStory(
        @Header("Authorization") token: String
    ): Call<ResponseStory>

    @GET("stories/{id}")
    fun getDetailStory(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<ResponseDetailStory>

    @GET("stories?location=1")
    fun getMaps(
        @Header("Authorization") token: String
    ): Call<ResponseStory>
}