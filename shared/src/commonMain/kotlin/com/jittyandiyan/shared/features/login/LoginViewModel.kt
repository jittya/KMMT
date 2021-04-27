package com.jittyandiyan.shared.features.login

import com.jittyandiyan.shared.Platform
import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel

class LoginViewModel(view: LoginView) :BaseViewModel<LoginView>(view) {
    override fun onStartViewModel() {
        getView()?.setLoginPageLabel("Login : ${Platform().platform}")
        getView()?.setUsernameLabel("Enter Username")
        getView()?.setPasswordLabel("Enter Password")
        getView()?.setLoginButtonLabel("Login")
        getView()?.setLoginButtonClickAction(this::onLoginButtonClick)
    }

    fun onLoginButtonClick()
    {
        val username=getView()?.getEnteredUsername()
        val password=getView()?.getEnteredPassword()
        checkValidation(username,password)

    }

    fun checkValidation(username:String?,password:String?)
    {
        if (username.isNullOrBlank().not()&&password.isNullOrBlank().not())
        {
            getView()?.showPopUpMessage("Login Success","Username : $username\nPassword : $password")
        }
        else
        {
            if (username.isNullOrBlank())
            {
                getView()?.showErrorMessageOnUsername("Please enter username")
            }
            getView()?.showPopUpMessage("Validation Failed","Username or Password is empty")
        }
    }
}