package com.example.githubuserapp.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.api.response.ItemsUsers
import com.example.githubuserapp.db.helper.ListUserAdapter
import com.example.githubuserapp.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding

    companion object {
        const val POSITION = "section_number"
        const val USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowBinding.bind(view)

        val position = arguments?.getInt(POSITION)
        val username = arguments?.getString(USERNAME)

        val followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowViewModel::class.java]

        followViewModel.userFollower.observe(viewLifecycleOwner) { user ->
            setListFollow(user)
        }

        followViewModel.userFollowing.observe(viewLifecycleOwner) { user ->
            setListFollow(user)
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager

        if (position == 1) {
            followViewModel.getListFollower(username!!)
            binding.tvError.text =
                "${followViewModel.errorFollower}\n${followViewModel.errorResponseFollower}"
            followViewModel.isNoFollower.observe(viewLifecycleOwner) {
                if (it) {
                    binding.NoFollow.text = "No Follower"
                }
            }
        } else {
            followViewModel.getListFollowing(username!!)
            binding.tvError.text =
                "${followViewModel.errorFollowing}\n${followViewModel.errorResponseFollowing}"
            followViewModel.isNoFollowing.observe(viewLifecycleOwner) {
                if (it) {
                    binding.NoFollow.text = "No Following"
                }
            }
        }
    }

    private fun setListFollow(user: List<ItemsUsers?>?) {
        val adapter = ListUserAdapter(user)
        binding.rvFollow.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: ItemsUsers?) {
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}