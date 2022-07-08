package com.jittyandiyan.androidApp.demo.features.tvshows.shows.adapter

import android.text.Html
import androidx.recyclerview.widget.RecyclerView
import com.jittyandiyan.androidApp.databinding.TvshowAdapterItemBinding
import com.kmmt.models.demotvshowsearch.domain.TVShowInfo

class TVShowViewHolder(binding: TvshowAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val showDescTV = binding.showDescTV
    private val showLanguageTV = binding.showLanguageTV
    private val showNameTV = binding.showNameTV

    fun bindData(showModel: TVShowInfo)
    {
        showModel.summary?.let {
            showDescTV.text = Html.fromHtml(it)
        }
        showModel.language?.let {
            showLanguageTV.text = it
        }
        showModel.name?.let {
            showNameTV.text = it
        }
    }
}