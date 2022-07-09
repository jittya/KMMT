package com.jittyandiyan.shared.demoTVMazeShowSearch.features.welcome

import com.kmmt.core.architecture.presenter.BasePresenter
import com.kmmt.resources.Resources
import com.kmmt.resources.expectations.localized


class WelcomePresenter(view: WelcomeView) : BasePresenter<WelcomeView>(view) {
    override fun onStartPresenter() {

        getView()?.setPageTitle(Resources.strings.codeChallenge.localized())
        getView()?.setWelcomePageLabel(Resources.strings.codeChallengeTVShowSearch.localized())
        getView()?.setTVShowsButtonLabel(Resources.strings.searchTVShows.localized())
        getView()?.setTVShowsButtonClickAction(this::onLoginButtonClick)
    }

    private fun onLoginButtonClick() {
        getView()?.navigateToTVShowsPage()
    }

}