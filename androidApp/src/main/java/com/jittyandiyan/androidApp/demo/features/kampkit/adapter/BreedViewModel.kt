package com.jittyandiyan.androidApp.demo.features.kampkit.adapter

import androidx.recyclerview.widget.RecyclerView
import com.jittyandiyan.androidApp.R
import com.jittyandiyan.androidApp.databinding.ItemBreedBinding
import com.jittyandiyan.mobile.TBreed

class BreedViewModel(binding: ItemBreedBinding) : RecyclerView.ViewHolder(binding.root) {
    private val nameTextView = binding.breedNameTextView
    private val favoriteButton = binding.favoriteButton

    fun bindData(breed: TBreed) {
        nameTextView.text = breed.name
        if (breed.favorite) {
            favoriteButton.setBackgroundResource(R.drawable.ic_favorite_24px)
        } else {
            favoriteButton.setBackgroundResource(R.drawable.ic_favorite_border_24px)
        }
    }
}