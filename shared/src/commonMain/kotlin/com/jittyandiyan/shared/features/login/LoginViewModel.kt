package com.jittyandiyan.shared.features.login

import com.jittyandiyan.shared.Platform
import com.jittyandiyan.shared.apis.JsonPlaceHolderServiceAPI
import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel

class LoginViewModel(view: LoginView) : BaseViewModel<LoginView>(view) {
    override fun onStartViewModel() {
        getView()?.setLoginPageLabel("Login : ${Platform().platform}")
        getView()?.setUsernameLabel("Enter Username")
        getView()?.setPasswordLabel("Enter Password")
        getView()?.setLoginButtonLabel("Login")
        getView()?.setLoginButtonClickAction(this::onLoginButtonClick)
    }

    private fun onLoginButtonClick() {
        val username = getView()?.getEnteredUsername()
        val password = getView()?.getEnteredPassword()
        checkValidation(username, password)

    }

    private fun checkValidation(username: String?, password: String?) {
        if (username.isNullOrBlank().not() && password.isNullOrBlank().not()) {

            runOnBackground(1){
                JsonPlaceHolderServiceAPI()::getPosts
            }.resultOnUI {
                getView()?.showPopUpMessage("Login Success", "Username : ${it.first().name}\n email : ${it.first().email}")
            }

        } else {
            if (username.isNullOrBlank()) {
                getView()?.showErrorMessageOnUsername("Please enter username")
            }
            getView()?.showPopUpMessage("Validation Failed", "Username or Password is empty")
        }
    }
}