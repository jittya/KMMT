package com.jittyandiyan.shared.demo.features.login

import com.kmmt.core.architecture.view.BaseView
import com.kmmt.core.platform.expectations.BundleParcel
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

    fun navigateToHomePage(bundleParcel: BundleParcel)
}