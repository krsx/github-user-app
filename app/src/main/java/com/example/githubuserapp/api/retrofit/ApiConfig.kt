package com.example.githubuserapp.api.retrofit

import com.example.githubuserapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {
        fun getApiService(): ApiService {
            val apiTokenKey = BuildConfig.KEY
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", "token $apiTokenKey")
                    .build()
                chain.proceed(requestHeaders)
            }

            val client = OkHttpClient.Builder().addInterceptor(authInterceptor).build()
            val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            return retrofit.create(ApiService::class.java)
        }
    }
}