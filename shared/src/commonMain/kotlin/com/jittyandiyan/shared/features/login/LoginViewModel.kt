package com.jittyandiyan.shared.features.login

import com.jittyandiyan.shared.Platform
import com.jittyandiyan.shared.apis.ProfileMicroServiceAPI
import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel
import com.jittyandiyan.shared.models.ProfileModel

class LoginViewModel(view: LoginView) : BaseViewModel<LoginView>(view) {
    override fun onStartViewModel() {
        getView()?.setLoginPageLabel("Login : ${Platform().platform}")
        getView()?.setUsernameLabel("Enter Username")
        getView()?.setPasswordLabel("Enter Password")
        getView()?.setLoginButtonLabel("Login")
        getView()?.setLoginButtonClickAction(this::onLoginButtonClick)
    }

    fun onLoginButtonClick() {
        val username = getView()?.getEnteredUsername()
        val password = getView()?.getEnteredPassword()
        checkValidation(username, password)

    }

    fun checkValidation(username: String?, password: String?) {
        if (username.isNullOrBlank().not() && password.isNullOrBlank().not()) {

            runOnBackground<ProfileModel>{
                ProfileMicroServiceAPI()::getProfile
            }.resultOnUI {
                getView()?.showPopUpMessage("Login Success", "Username : ${it.name}\n Github : ${it.github}")
            }

        } else {
            if (username.isNullOrBlank()) {
                getView()?.showErrorMessageOnUsername("Please enter username")
            }
            getView()?.showPopUpMessage("Validation Failed", "Username or Password is empty")
        }
    }
}