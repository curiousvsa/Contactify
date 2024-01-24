package com.example.contactify.retrofit

import com.example.contactify.data.UsersModel
import retrofit2.Call
import retrofit2.http.GET

interface RetroServiceInterface {
    @GET("users")
    fun getUsersList(): Call<UsersModel> //API Response DataClass
}