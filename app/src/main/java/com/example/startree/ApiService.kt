package com.example.startree

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface ApiService {
    @Multipart
    @POST("/predict")
    fun postPredict(@Part imageFile : MultipartBody.Part) : Call<ResponseBody>
}