package com.jittyandiyan.shared.demo.features.login

import com.jittyandiyan.shared.Platform
import com.jittyandiyan.shared.core.architecture.presenter.BasePresenter
import com.jittyandiyan.shared.core.platform.runOnAndroid
import com.jittyandiyan.shared.demo.dataSources.apis.JsonPlaceHolderServiceAPI
import com.jittyandiyan.shared.demo.features.home.HomePresenter
import com.jittyandiyan.shared.demo.models.CredentialsModel
import com.jittyandiyan.shared.demo.models.UserModel


class LoginPresenter(view: LoginView) : BasePresenter<LoginView>(view) {
    override fun onStartPresenter() {
        runOnAndroid {
            getView()?.setPageTitle("KMM Login")
        }
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
            runOnBackgroundWithResult {
                JsonPlaceHolderServiceAPI().authenticate(credentials)
            }.resultOnUI { authenticatedResult ->
                getView()?.dismissLoading()
                authenticatedResult.either({
                    getView()?.showPopUpMessage(it.message)
                }, { isAuthenticated ->
                    if (isAuthenticated) {

                        var userModel = UserModel("jittya@gmail.com", "Jitty", "Andiyan")

                        var bundle = Bundle {
                            putStringExtra(HomePresenter.USER_NAME, username.toString())
                            putSerializableExtra(HomePresenter.USER_OBJECT, userModel, UserModel.serializer())
                        }

                        getView()?.navigateToHomePage(bundle)
                    } else {
                        getView()?.showPopUpMessage(
                            "Login Failed"
                        )
                    }
                })

            }
        } else {
            if (username.isNullOrBlank()) {
                getView()?.showErrorMessageOnUsername("Please enter username")
            }
            getView()?.showPopUpMessage("Validation Failed", "Username or Password is empty")
        }
    }
}