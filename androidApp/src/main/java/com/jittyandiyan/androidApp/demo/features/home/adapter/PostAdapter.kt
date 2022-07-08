package com.jittyandiyan.androidApp.demo.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jittyandiyan.androidApp.databinding.PostAdapterItemBinding
import com.kmmt.models.demo.domain.PostModel

class PostAdapter : ListAdapter<PostModel, PostViewHolder>(postCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        var binding = PostAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    companion object {
        private val postCallback = object : DiffUtil.ItemCallback<PostModel>() {
            override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean = oldItem == newItem
            override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean = oldItem.id == newItem.id
        }
    }
}