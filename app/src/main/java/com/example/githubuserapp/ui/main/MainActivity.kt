package com.example.githubuserapp.ui.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.databinding.ActivityMainBinding
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.githubuserapp.api.response.ItemsUsers
import com.example.githubuserapp.R
import com.example.githubuserapp.db.helper.ListUserAdapter
import com.example.githubuserapp.db.helper.ViewModelFactory
import com.example.githubuserapp.ui.favourite.FavouriteActivity
import com.example.githubuserapp.ui.setting.SettingPreferences
import com.example.githubuserapp.ui.setting.SettingViewModel
import com.example.githubuserapp.ui.setting.SettingsActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar?.title = "Home"
        actionBar?.setBackgroundDrawable(getDrawable(R.color.dark_200))

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        mainViewModel.user.observe(this) { user ->
            setUserData(user)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.tvErrorMain.text = "${mainViewModel.error}\n${mainViewModel.errorResponse}"

        mainViewModel.isNoUser.observe(this) {
            if (it) {
                binding.tvNoUser.text = "No User Found"
            } else {
                binding.tvNoUser.text = ""
            }
        }

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            ViewModelFactory(application, pref)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mainViewModel.findUsers(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favourite -> {
                val intentFavourite = Intent(this@MainActivity, FavouriteActivity::class.java)
                startActivity(intentFavourite)
            }
            R.id.settingButton -> {
                val intentSettings = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intentSettings)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun setUserData(user: List<ItemsUsers?>?) {
        val adapter = ListUserAdapter(user)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: ItemsUsers?) {
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}