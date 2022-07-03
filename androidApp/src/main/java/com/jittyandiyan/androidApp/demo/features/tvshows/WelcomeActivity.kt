package com.jittyandiyan.androidApp.demo.features.tvshows

import com.jittyandiyan.androidApp.databinding.ActivityWelcomeBinding
import com.jittyandiyan.androidApp.demo.features.tvshows.shows.TVShowsSearchActivity
import com.kmmt.core.architecture.view.KMMActivity
import com.kmmt.core.extensions.setClickAction
import com.jittyandiyan.shared.demoTVMazeShowSearch.features.welcome.WelcomePresenter
import com.jittyandiyan.shared.demoTVMazeShowSearch.features.welcome.WelcomeView
import kotlin.reflect.KFunction0

class WelcomeActivity : KMMActivity<WelcomePresenter, ActivityWelcomeBinding>(), WelcomeView {

    //Generated Methods from KMMActivity based on WelcomeViewModel
    override fun initializePresenter(): WelcomePresenter {
        return WelcomePresenter(this)
    }

    override fun viewBindingInflate(): ActivityWelcomeBinding {
        return ActivityWelcomeBinding.inflate(layoutInflater)
    }

    //Generated Methods from WelcomeView
    override fun setWelcomePageLabel(msg: String) {
        binding.textView.text = msg
    }

    override fun setTVShowsButtonClickAction(onLoginClick: KFunction0<Unit>) {
        binding.welcomeBtn.setClickAction(onLoginClick)
    }

    override fun setTVShowsButtonLabel(tvShowsBtnLbl: String) {
        binding.welcomeBtn.text = tvShowsBtnLbl
    }

    override fun navigateToTVShowsPage() {
        openActivity(TVShowsSearchActivity::class.java)
        finish()
    }

}
