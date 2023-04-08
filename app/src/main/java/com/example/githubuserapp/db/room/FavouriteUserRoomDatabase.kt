package com.example.githubuserapp.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserapp.db.entity.FavouriteUser

@Database(entities = [FavouriteUser::class], version = 1)
abstract class FavouriteUserRoomDatabase : RoomDatabase() {
    abstract fun favouriteUserDao(): FavouriteUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteUserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavouriteUserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavouriteUserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavouriteUserRoomDatabase::class.java,
                        "favourite_user_database"
                    ).build()
                }
            }

            return INSTANCE as FavouriteUserRoomDatabase
        }
    }
}