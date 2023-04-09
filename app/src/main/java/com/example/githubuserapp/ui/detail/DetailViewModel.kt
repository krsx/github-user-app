package com.example.githubuserapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.api.retrofit.ApiConfig
import com.example.githubuserapp.api.response.GithubUserDetailResponse
import com.example.githubuserapp.db.entity.FavouriteUser
import com.example.githubuserapp.db.repository.FavouriteUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class DetailViewModel(application: Application) : ViewModel() {
    var errorResponse: String = ""
    var error: String = ""

    private val mFavouriteUserRepository: FavouriteUserRepository =
        FavouriteUserRepository(application)

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

    fun insert(favouriteUser: FavouriteUser) {
        viewModelScope.launch(Dispatchers.IO) {
            mFavouriteUserRepository.insert(favouriteUser)
        }
    }

    fun delete(favouriteUser: FavouriteUser) {
        mFavouriteUserRepository.delete(favouriteUser)
    }

    fun getFavouriteUserByUsername(username: String): LiveData<FavouriteUser> =
        mFavouriteUserRepository.getFavouriteUserByUsername(username)


}