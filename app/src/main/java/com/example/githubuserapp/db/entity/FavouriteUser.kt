package com.example.githubuserapp.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favourite_user")
@Parcelize
data class FavouriteUser(
    @PrimaryKey(autoGenerate = false)

    @ColumnInfo(name = "username")
    var username: String = "",

    @ColumnInfo(name = "avatar_img")
    var avatarImgUrl: String? = null,
) : Parcelable


