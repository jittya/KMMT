package com.jittyandiyan.shared.features.login

import com.jittyandiyan.shared.Platform
import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel
import com.jittyandiyan.shared.dataSources.apis.JsonPlaceHolderServiceAPI
import com.jittyandiyan.shared.features.home.HomeViewModel
import com.jittyandiyan.shared.models.CredentialsModel
import com.jittyandiyan.shared.models.UserModel


class LoginViewModel(view: LoginView) : BaseViewModel<LoginView>(view) {
    override fun onStartViewModel() {
        getView()?.setLoginPageLabel("Login : ${Platform().platform}")
        getView()?.setUsernameLabel("Enter Username")
        getView()?.setPasswordLabel("Enter Password")
        getView()?.setLoginButtonLabel("Login")
        getView()?.setLoginButtonClickAction(this::onLoginButtonClick)
    }

    private fun onLoginButtonClick() {
        getView()?.showLoading("authenticating...")
        val username = getView()?.getEnteredUsername()
        val password = getView()?.getEnteredPassword()
        checkValidation(username, password)

    }

    private fun checkValidation(username: String?, password: String?) {
        if (username.isNullOrBlank().not() && password.isNullOrBlank().not()) {
            val credentials = CredentialsModel(username.toString(), password.toString())
            runOnBackground(credentials) {
                JsonPlaceHolderServiceAPI()::authenticate
            }.resultOnUI {
                getView()?.dismissLoading()
                if (it) {

                    var userModel = UserModel("jittya@gmail.com", "Jitty", "Andiyan")

                    getView()?.navigateToHomePage(Bundle {
                        putStringExtra(HomeViewModel.USER_NAME, username.toString())
                        putSerializableExtra(HomeViewModel.USER_OBJECT, userModel, UserModel.serializer())
                    })
                } else {
                    getView()?.showPopUpMessage(
                        "Login Failed"
                    )
                }
            }
        } else {
            if (username.isNullOrBlank()) {
                getView()?.showErrorMessageOnUsername("Please enter username")
            }
            getView()?.showPopUpMessage("Validation Failed", "Username or Password is empty")
        }
    }
}