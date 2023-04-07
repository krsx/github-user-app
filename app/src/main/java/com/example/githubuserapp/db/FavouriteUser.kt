package com.example.githubuserapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteUser (
    @PrimaryKey(autoGenerate = true)
    var username: String = "",
    var avatarImgUrl: String? = null,
)


