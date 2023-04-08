package com.example.githubuserapp.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.api.retrofit.ApiConfig
import com.example.githubuserapp.api.response.GithubUserResponse
import com.example.githubuserapp.api.response.ItemsUsers
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class MainViewModel() : ViewModel() {
    var errorResponse: String = ""
    var error: String = ""

    companion object {
        private const val TAG = "MainViewModel"
        private const val USER_ID = "krisna"
    }

    private val _user = MutableLiveData<List<ItemsUsers?>?>()
    val user: LiveData<List<ItemsUsers?>?> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isNoUser = MutableLiveData<Boolean>()
    val isNoUser: LiveData<Boolean> = _isNoUser

    init {
        findUsers(USER_ID)
    }

    fun findUsers(user: String = USER_ID) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUser(user)
        client.enqueue(
            object : Callback<GithubUserResponse> {
                override fun onResponse(
                    call: Call<GithubUserResponse>,
                    response: Response<GithubUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _user.value = response.body()?.items
                        _isNoUser.value = _user.value.isNullOrEmpty()
                    } else {
                        errorResponse = "onFailure: ${response.message()} + ${response.code()}"
                        Log.e(TAG, errorResponse)
                    }
                }

                override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    error = "onFailure: ${t.message.toString()} "
                    Log.e(TAG, error)
                }
            }
        )
    }

}