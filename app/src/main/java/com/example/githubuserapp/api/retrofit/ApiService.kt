package com.example.githubuserapp.api.retrofit

import com.example.githubuserapp.api.response.GithubUserDetailResponse
import com.example.githubuserapp.api.response.GithubUserResponse
import com.example.githubuserapp.api.response.ItemsUsers
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getGithubUser(
        @Query("q") q: String
    ): Call<GithubUserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<GithubUserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsUsers>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsUsers>>
}