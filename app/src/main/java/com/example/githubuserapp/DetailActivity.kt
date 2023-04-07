package com.example.githubuserapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val KEY_USER = "key_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(KEY_USER)

        val detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val tabs: TabLayout = findViewById(R.id.tabs)

        detailViewModel.findDetailUser(username)

        detailViewModel.userDetail.observe(this) { user ->
            setDetailUserData(user)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewPager.adapter = sectionsPagerAdapter
        sectionsPagerAdapter.username = username.toString()

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.tvError.text = "${detailViewModel.error}\n${detailViewModel.errorResponse}"
    }


    private fun setDetailUserData(user: GithubUserDetailResponse) {
        Glide.with(this).load(user.avatarUrl).into(binding.imgUser)
        binding.name.text = user.name ?: "Username"
        binding.username.text = user.login
        binding.tvFollowers.text = this.resources.getString(R.string.followers, user.followers)
        binding.tvFollowing.text = this.resources.getString(R.string.following, user.following)

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) {
            binding.name.text = ""
            binding.username.text = ""
            View.VISIBLE
        } else View.GONE
    }


}