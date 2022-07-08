package com.jittyandiyan.androidApp.demo.features.kampkit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jittyandiyan.androidApp.databinding.ItemBreedBinding
import com.kmmt.models.demo.domain.Breed
import kotlin.reflect.KFunction1

class BreedAdapter : ListAdapter<Breed, BreedViewHolder>(postCallback) {
    private var invertBreedFavouriteState: KFunction1<Breed, Unit>? = null

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

    fun setBreedFavouriteClickAction(invertBreedFavouriteState: KFunction1<Breed, Unit>) {
        this.invertBreedFavouriteState = invertBreedFavouriteState
    }

    companion object {
        private val postCallback = object : DiffUtil.ItemCallback<Breed>() {
            override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean = oldItem == newItem
            override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean = oldItem.id == newItem.id
        }
    }
}