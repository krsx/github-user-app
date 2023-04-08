package com.example.githubuserapp.db.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserapp.db.entity.FavouriteUser

@Dao
interface FavouriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favouriteUser: FavouriteUser)

    @Delete
    fun delete(favouriteUser: FavouriteUser)

    @Query("SELECT * FROM favourite_user")
    fun getAllFavouriteUser(): LiveData<List<FavouriteUser>>
}