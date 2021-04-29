package com.jittyandiyan.shared.features.login

import com.jittyandiyan.shared.Platform
import com.jittyandiyan.shared.apis.JsonPlaceHolderServiceAPI
import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel
import com.jittyandiyan.shared.models.CredentialsModel


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
                    getView()?.navigateToHomePage(username.toString())
                } else {
                    getView()?.showPopUpMessage(
                        "Login Failed"
                    )
                }
            }

//            runOnBackground(1) {
//                JsonPlaceHolderServiceAPI()::getPosts
//            }.cacheOnDB ({ postList ->
//                postList.forEach {
//                    tPostQueries.deletePosts(it.id.toLong())
//                    tPostQueries
//                        .addPost(it.id.toLong(), it.name, it.email, it.postId?.toLong(), it.body)
//                }
//            },{
//                it.resultOnUI {
//                    var postList:List<TPost> = get<KMMTDB>().tPostQueries.getPosts(1).executeAsList()
//                    getView()?.showPopUpMessage(
//                        "Login Success",
//                        "Username : ${it.first().name}\n email : ${it.first().email}"
//                    )
//                }
//            })

        } else {
            if (username.isNullOrBlank()) {
                getView()?.showErrorMessageOnUsername("Please enter username")
            }
            getView()?.showPopUpMessage("Validation Failed", "Username or Password is empty")
        }
    }
}