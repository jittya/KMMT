package com.jittyandiyan.androidApp.demo.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jittyandiyan.androidApp.databinding.PostAdapterItemBinding
import com.jittyandiyan.shared.demo.models.PostModel

class PostAdapter : ListAdapter<PostModel, PostViewModel>(postCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewModel {
        var binding = PostAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewModel(binding)
    }

    override fun onBindViewHolder(holder: PostViewModel, position: Int) {
        holder.bindData(getItem(position))
    }

    companion object {
        private val postCallback = object : DiffUtil.ItemCallback<PostModel>() {
            override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean = oldItem == newItem
            override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean = oldItem.id == newItem.id
        }
    }
}