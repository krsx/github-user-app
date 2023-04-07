package com.example.githubuserapp.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.api.model.ItemsUsers
import com.example.githubuserapp.databinding.ItemUserBinding
import com.example.githubuserapp.ui.detail.DetailActivity

class ListUserAdapter(private val listUser: List<ItemsUsers?>?) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(listUser?.get(position)?.avatarUrl)
            .into(holder.binding.imageUser)
        holder.binding.tvUsername.text = listUser?.get(position)!!.login

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[position])
            val moveToDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            moveToDetail.putExtra("key_user", listUser[position]?.login)
            holder.itemView.context.startActivity(moveToDetail)
        }
    }

    override fun getItemCount(): Int = listUser!!.size

    interface OnItemClickCallback {
        fun onItemClicked(user: ItemsUsers?)
    }
}