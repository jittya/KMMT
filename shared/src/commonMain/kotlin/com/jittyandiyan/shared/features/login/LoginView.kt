package com.jittyandiyan.shared.features.login

import com.jittyandiyan.shared.core.architecture.view.BaseView
import kotlin.reflect.KFunction0

interface LoginView : BaseView {

    fun setLoginPageLabel(msg:String)

    fun setUsernameLabel(usernameLabel:String)
    fun setPasswordLabel(passwordLabel:String)

    fun getEnteredUsername():String
    fun getEnteredPassword():String

    fun setLoginButtonClickAction(onLoginClick: KFunction0<Unit>)
    fun setLoginButtonLabel(loginLabel: String)

    fun showErrorMessageOnUsername(errorMsg: String)

    fun navigateToHomePage(username:String)
}