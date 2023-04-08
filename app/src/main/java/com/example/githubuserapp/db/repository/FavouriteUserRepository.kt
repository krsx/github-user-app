package com.example.githubuserapp.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapp.db.entity.FavouriteUser
import com.example.githubuserapp.db.room.FavouriteUserDao
import com.example.githubuserapp.db.room.FavouriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavouriteUserRepository(application: Application) {
    private val mFavouriteUserDao: FavouriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavouriteUserRoomDatabase.getDatabase(application)
        mFavouriteUserDao = db.favouriteUserDao()
    }

    fun getAllFavouriteUser(): LiveData<List<FavouriteUser>> =
        mFavouriteUserDao.getAllFavouriteUser()

    fun insert(favouriteUser: FavouriteUser) {
        executorService.execute {
            mFavouriteUserDao.insert(favouriteUser)
        }
    }

    fun delete(favouriteUser: FavouriteUser) {
        executorService.execute {
            mFavouriteUserDao.delete(favouriteUser)
        }
    }

}