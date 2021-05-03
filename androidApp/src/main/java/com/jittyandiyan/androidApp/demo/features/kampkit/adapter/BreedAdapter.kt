package com.jittyandiyan.androidApp.demo.features.kampkit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jittyandiyan.androidApp.databinding.ItemBreedBinding
import com.jittyandiyan.mobile.TBreed
import kotlin.reflect.KFunction1

class BreedAdapter : ListAdapter<TBreed, BreedViewHolder>(postCallback) {
    private var invertBreedFavouriteState: KFunction1<TBreed, Unit>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        var binding = ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bindData(getItem(position))
        holder.favoriteButton.setOnClickListener {
            invertBreedFavouriteState?.invoke(getItem(position))
        }
    }

    fun setBreedFavouriteClickAction(invertBreedFavouriteState: KFunction1<TBreed, Unit>) {
        this.invertBreedFavouriteState = invertBreedFavouriteState
    }

    companion object {
        private val postCallback = object : DiffUtil.ItemCallback<TBreed>() {
            override fun areContentsTheSame(oldItem: TBreed, newItem: TBreed): Boolean = oldItem == newItem
            override fun areItemsTheSame(oldItem: TBreed, newItem: TBreed): Boolean = oldItem.id == newItem.id
        }
    }
}