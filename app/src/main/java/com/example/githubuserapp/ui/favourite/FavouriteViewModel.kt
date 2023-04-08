package com.example.githubuserapp.ui.favourite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.db.entity.FavouriteUser
import com.example.githubuserapp.db.repository.FavouriteUserRepository

class FavouriteViewModel(application: Application) : ViewModel() {
    private val mFavouriteUserRepository: FavouriteUserRepository =
        FavouriteUserRepository(application)

    fun insert(favouriteUser: FavouriteUser) {
        mFavouriteUserRepository.insert(favouriteUser)
    }

    fun delete(favouriteUser: FavouriteUser) {
        mFavouriteUserRepository.delete(favouriteUser)
    }
}