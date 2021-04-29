package com.jittyandiyan.androidApp.features

import com.jittyandiyan.androidApp.databinding.ActivityHomeBinding
import com.jittyandiyan.shared.core.architecture.view.KMMActivity
import com.jittyandiyan.shared.features.home.HomeView
import com.jittyandiyan.shared.features.home.HomeViewModel
import com.jittyandiyan.shared.models.PostModel

class HomeActivity : KMMActivity<HomeViewModel, ActivityHomeBinding>(), HomeView {

    override fun initializeViewModel(): HomeViewModel {
        return HomeViewModel(this)
    }

    override fun viewBindingInflate(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun showPostList(postList: List<PostModel>) {

    }

    override fun showUsername(username: String) {
        binding.usernameTV.text=username
    }

}