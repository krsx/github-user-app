package com.example.githubuserapp.ui.favourite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.api.response.ItemsUsers
import com.example.githubuserapp.databinding.ActivityFavouriteBinding
import com.example.githubuserapp.db.helper.ListUserAdapter
import com.example.githubuserapp.db.helper.ViewModelFactory


class FavouriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        actionBar?.title = "Favorite"
        actionBar?.setBackgroundDrawable(getDrawable(R.color.dark_200))

        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favouriteViewModel = obtainViewModel(this)
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        favouriteViewModel.getAllFavouriteUser().observe(this) { user ->
            val items = arrayListOf<ItemsUsers>()
            user.map {
                val item = ItemsUsers(login = it.username, avatarUrl = it.avatarImgUrl)
                items.add(item)
            }
            setFavUserData(items)
        }
    }

    private fun setFavUserData(user: List<ItemsUsers>?) {
        val adapter = ListUserAdapter(user)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: ItemsUsers?) {
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavouriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavouriteViewModel::class.java]
    }
}