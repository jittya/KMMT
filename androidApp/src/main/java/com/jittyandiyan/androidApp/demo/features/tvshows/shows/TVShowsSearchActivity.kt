package com.jittyandiyan.androidApp.demo.features.tvshows.shows

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.jittyandiyan.androidApp.databinding.ActivityTvshowsBinding
import com.jittyandiyan.androidApp.demo.features.tvshows.shows.adapter.TVShowsAdapter
import com.kmmt.core.architecture.view.KMMActivity
import com.jittyandiyan.shared.demoTVMazeShowSearch.features.tvshows.TVShowsSearchPresenter
import com.jittyandiyan.shared.demoTVMazeShowSearch.features.tvshows.TVShowsSearchView
import com.kmmt.models.demotvshowsearch.domain.TVShowInfo
import kotlin.reflect.KFunction1

class TVShowsSearchActivity : KMMActivity<TVShowsSearchPresenter, ActivityTvshowsBinding>(),
    TVShowsSearchView {

    private lateinit var adapter: TVShowsAdapter

    override fun initializePresenter(): TVShowsSearchPresenter {
        return TVShowsSearchPresenter(this)
    }

    override fun viewBindingInflate(): ActivityTvshowsBinding {
        return ActivityTvshowsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = TVShowsAdapter()
        binding.showListRV.adapter = adapter
        binding.showListRV.layoutManager = LinearLayoutManager(this)
    }

    override fun showTVShowsList(tvShowList: List<TVShowInfo>) {
        adapter.submitList(tvShowList)
    }

    override fun setSearchQueryChangeListener(onSearchQueryStringChanged: KFunction1<String, Unit>) {
        binding.searchET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(query: Editable?) {
                query?.let {
                    onSearchQueryStringChanged(it.toString())
                }
            }

        })
    }

}