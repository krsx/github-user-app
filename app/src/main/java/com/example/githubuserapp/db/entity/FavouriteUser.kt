package com.example.githubuserapp.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_user")
data class FavouriteUser(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarImgUrl: String? = null,
)


