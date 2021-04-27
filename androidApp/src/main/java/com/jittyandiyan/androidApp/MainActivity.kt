package com.jittyandiyan.androidApp

import android.os.Bundle
import com.jittyandiyan.androidApp.databinding.ActivityMainBinding
import com.jittyandiyan.shared.core.architecture.view.KMMActivity
import com.jittyandiyan.shared.features.login.LoginView
import com.jittyandiyan.shared.features.login.LoginViewModel
import kotlin.reflect.KFunction0

class MainActivity : KMMActivity<LoginViewModel>(), LoginView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private lateinit var binding: ActivityMainBinding

    //Generated Methods from LoginView
    override fun setLoginPageLabel(msg: String) {
        binding.textView.text = msg
    }

    override fun setUsernameLabel(usernameLabel: String) {
        binding.usernameET.hint=usernameLabel
    }

    override fun setPasswordLabel(passwordLabel: String) {
        binding.passwordET.hint=passwordLabel
    }

    override fun getEnteredUsername(): String {
        return binding.passwordET.text.toString()
    }

    override fun getEnteredPassword(): String {
        return binding.usernameET.text.toString()
    }

    override fun setLoginButtonClickAction(onLoginClick: KFunction0<Unit>) {
        binding.loginBtn.setOnClickListener {
            onLoginClick.invoke()
        }
    }

    override fun setLoginButtonLabel(loginLabel: String) {
        binding.loginBtn.text=loginLabel
    }

    override fun showErrorMessageOnUsername(errorMsg: String) {
        binding.usernameET.error = errorMsg
    }


    //Generated Methods from LoginViewModel
    override fun initializeViewModel(): LoginViewModel {
        return LoginViewModel(this)
    }


}
