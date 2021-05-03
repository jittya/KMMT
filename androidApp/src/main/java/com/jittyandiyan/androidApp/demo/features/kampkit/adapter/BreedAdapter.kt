package com.jittyandiyan.androidApp.demo.features.kampkit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jittyandiyan.androidApp.databinding.ItemBreedBinding
import com.jittyandiyan.mobile.TBreed

class BreedAdapter : ListAdapter<TBreed, BreedViewHolder>(postCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        var binding = ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    companion object {
        private val postCallback = object : DiffUtil.ItemCallback<TBreed>() {
            override fun areContentsTheSame(oldItem: TBreed, newItem: TBreed): Boolean = oldItem == newItem
            override fun areItemsTheSame(oldItem: TBreed, newItem: TBreed): Boolean = oldItem.id == newItem.id
        }
    }
}