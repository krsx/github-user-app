package com.example.githubuserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class FollowViewModel : ViewModel() {
    var errorResponseFollower: String = ""
    var errorFollower: String = ""

    var errorResponseFollowing: String = ""
    var errorFollowing: String = ""

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _userFollower = MutableLiveData<List<ItemsUsers?>?>()
    val userFollower: LiveData<List<ItemsUsers?>?> = _userFollower

    private val _userFollowing = MutableLiveData<List<ItemsUsers?>?>()
    val userFollowing: LiveData<List<ItemsUsers?>?> = _userFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isNoFollower = MutableLiveData<Boolean>()
    val isNoFollower: LiveData<Boolean> = _isNoFollower

    private var _isNoFollowing = MutableLiveData<Boolean>()
    var isNoFollowing: LiveData<Boolean> = _isNoFollowing

    fun getListFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)

        client.enqueue(object : Callback<List<ItemsUsers>> {
            override fun onResponse(
                call: Call<List<ItemsUsers>>, response: Response<List<ItemsUsers>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userFollower.value = response.body()
                    _isNoFollower.value = _userFollower.value.isNullOrEmpty()
                } else {
                    errorResponseFollower = "onFailure: ${response.message()} + ${response.code()}"
                    Log.e(TAG, errorResponseFollower)
                }
            }

            override fun onFailure(call: Call<List<ItemsUsers>>, t: Throwable) {
                _isLoading.value = false
                errorFollower = "onFailure: ${t.message.toString()} "
                Log.e(TAG, errorFollower)
            }
        })
    }

    fun getListFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)

        client.enqueue(object : Callback<List<ItemsUsers>> {
            override fun onResponse(
                call: Call<List<ItemsUsers>>, response: Response<List<ItemsUsers>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userFollowing.value = response.body()
                    _isNoFollowing.value = _userFollowing.value.isNullOrEmpty()
                } else {
                    errorResponseFollowing = "onFailure: ${response.message()} + ${response.code()}"
                    Log.e(TAG, errorResponseFollowing)
                }
            }

            override fun onFailure(call: Call<List<ItemsUsers>>, t: Throwable) {
                errorFollowing = "onFailure: ${t.message.toString()} "
                Log.e(TAG, errorFollowing)
            }
        })
    }
}