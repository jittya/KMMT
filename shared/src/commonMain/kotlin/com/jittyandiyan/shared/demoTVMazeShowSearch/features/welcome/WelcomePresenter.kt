package com.jittyandiyan.shared.demoTVMazeShowSearch.features.welcome

import com.jittyandiyan.shared.core.architecture.presenter.BasePresenter


class WelcomePresenter(view: WelcomeView) : BasePresenter<WelcomeView>(view) {
    override fun onStartPresenter() {

        getView()?.setPageTitle("Code Challenge")
        getView()?.setWelcomePageLabel("Code Challenge : Search TV Shows \n\nby\n\n Jitty Andiyan")
        getView()?.setTVShowsButtonLabel("Search TV Shows")
        getView()?.setTVShowsButtonClickAction(this::onLoginButtonClick)
    }

    private fun onLoginButtonClick() {
        getView()?.navigateToTVShowsPage()
    }

}