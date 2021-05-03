package com.jittyandiyan.androidApp.demo.features.kampkit

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jittyandiyan.androidApp.databinding.ActivityKampKitBinding
import com.jittyandiyan.androidApp.demo.features.kampkit.adapter.BreedAdapter
import com.jittyandiyan.mobile.TBreed
import com.jittyandiyan.shared.core.architecture.view.KMMActivity
import com.jittyandiyan.shared.demo.features.kampkit.BreedView
import com.jittyandiyan.shared.demo.features.kampkit.BreedViewModel
import kotlin.reflect.KFunction1

class BreedActivity : KMMActivity<BreedViewModel, ActivityKampKitBinding>(), BreedView {

    private lateinit var breedAdapter: BreedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        breedAdapter = BreedAdapter()
        binding.breedList.adapter = breedAdapter
        binding.breedList.layoutManager = LinearLayoutManager(this)
    }

    override fun initializeViewModel(): BreedViewModel {
        return BreedViewModel(this)
    }

    override fun viewBindingInflate(): ActivityKampKitBinding {
        return ActivityKampKitBinding.inflate(layoutInflater)
    }

    override fun refreshBreedList(breedList: List<TBreed>) {
        breedAdapter.submitList(breedList)

    }

    override fun setBreedRefreshAction(refresh: KFunction1<Boolean, Unit>) {
        binding.swipeRefresh.setOnRefreshListener {
            refresh.invoke(true)
        }
    }

    override fun stopRefreshing() {
        binding.swipeRefresh.isRefreshing = false
    }

    override fun setBreedFavouriteClickAction(invertBreedFavouriteState: KFunction1<TBreed, Unit>) {
        breedAdapter.setBreedFavouriteClickAction(invertBreedFavouriteState)
    }
}