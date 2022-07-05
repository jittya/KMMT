package com.jittyandiyan.androidApp.demo.features

import com.jittyandiyan.androidApp.databinding.ActivityMainBinding
import com.jittyandiyan.androidApp.demo.features.home.HomeActivity
import com.kmmt.core.architecture.view.KMMActivity
import com.kmmt.core.extensions.setClickAction
import com.kmmt.core.platform.expectations.BundleParcel
import com.jittyandiyan.shared.demo.features.login.LoginView
import com.jittyandiyan.shared.demo.features.login.LoginPresenter
import kotlin.reflect.KFunction0

class LoginActivity : KMMActivity<LoginPresenter, ActivityMainBinding>(), LoginView {

    //Generated Methods from KMMActivity based on LoginViewModel
    override fun initializePresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    override fun viewBindingInflate(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    //Generated Methods from LoginView
    override fun setLoginPageLabel(msg: String) {
        binding.textView.text = msg
    }

    override fun setUsernameLabel(usernameLabel: String) {
        binding.usernameET.hint = usernameLabel
    }

    override fun setPasswordLabel(passwordLabel: String) {
        binding.passwordET.hint = passwordLabel
    }

    override fun getEnteredUsername(): String {
        return binding.usernameET.text.toString()
    }

    override fun getEnteredPassword(): String {
        return binding.passwordET.text.toString()
    }

    override fun setLoginButtonClickAction(onLoginClick: KFunction0<Unit>) {
        binding.loginBtn.setClickAction(onLoginClick)
    }

    override fun setLoginButtonLabel(loginLabel: String) {
        binding.loginBtn.text = loginLabel
    }

    override fun showErrorMessageOnUsername(errorMsg: String) {
        binding.usernameET.error = errorMsg
    }

    override fun navigateToHomePage(bundleParcel: BundleParcel) {
        openActivity(HomeActivity::class.java,bundleParcel)
        finish()
    }

}
