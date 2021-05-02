package com.jittyandiyan.androidApp.demo.features.kampkit

import com.jittyandiyan.androidApp.databinding.ActivityKampKitBinding
import com.jittyandiyan.shared.core.architecture.view.KMMActivity
import com.jittyandiyan.shared.demo.features.kampkit.BreedView
import com.jittyandiyan.shared.demo.features.kampkit.BreedViewModel

class KampKitActivity : KMMActivity<BreedViewModel, ActivityKampKitBinding>(), BreedView {


    override fun initializeViewModel(): BreedViewModel {
        return BreedViewModel(this)
    }

    override fun viewBindingInflate(): ActivityKampKitBinding {
        return ActivityKampKitBinding.inflate(layoutInflater)
    }
}