package com.jittyandiyan.androidApp.demo.features.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.jittyandiyan.androidApp.databinding.PostAdapterItemBinding
import com.kmmt.models.demo.domain.PostModel

class PostViewHolder(binding: PostAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val postDescTV = binding.postDescTV
    private val postEmailTV = binding.postEmailTV
    private val postNameTV = binding.postNameTV

    fun bindData(postModel: PostModel)
    {
        postDescTV.text = postModel.body
        postEmailTV.text = postModel.email
        postNameTV.text = postModel.name
    }
}