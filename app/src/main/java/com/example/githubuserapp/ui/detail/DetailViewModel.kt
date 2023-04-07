package com.example.githubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.api.helper.ApiConfig
import com.example.githubuserapp.api.model.GithubUserDetailResponse
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class DetailViewModel : ViewModel() {
    var errorResponse: String = ""
    var error: String = ""


    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userDetail = MutableLiveData<GithubUserDetailResponse>()
    val userDetail: LiveData<GithubUserDetailResponse> = _userDetail

    fun findDetailUser(user: String?) {
        _isLoading.value = true
        val client = user?.let { ApiConfig.getApiService().getDetailUser(it) }
        client?.enqueue(
            object : Callback<GithubUserDetailResponse> {
                override fun onResponse(
                    call: Call<GithubUserDetailResponse>,
                    response: Response<GithubUserDetailResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _userDetail.value = response.body()
                    } else {
                        errorResponse = "onFailure: ${response.message()} + ${response.code()}"
                        Log.e(TAG, errorResponse)
                    }
                }

                override fun onFailure(call: Call<GithubUserDetailResponse>, t: Throwable) {
                    _isLoading.value = false
                    error = "onFailure: ${t.message.toString()} "
                    Log.e(TAG, error)
                }

            }
        )
    }

}