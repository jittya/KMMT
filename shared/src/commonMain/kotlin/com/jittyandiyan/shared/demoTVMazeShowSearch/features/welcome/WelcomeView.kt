package com.jittyandiyan.shared.demoTVMazeShowSearch.features.welcome

import com.kmmt.core.architecture.view.BaseView
import kotlin.reflect.KFunction0

interface WelcomeView : BaseView {

    fun setWelcomePageLabel(msg:String)

    fun setTVShowsButtonClickAction(onLoginClick: KFunction0<Unit>)
    fun setTVShowsButtonLabel(tvShowsBtnLbl: String)

    fun navigateToTVShowsPage()
}