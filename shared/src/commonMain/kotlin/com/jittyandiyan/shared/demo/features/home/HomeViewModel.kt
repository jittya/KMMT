package com.jittyandiyan.shared.demo.features.home

import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel
import com.jittyandiyan.shared.core.extensions.toJsonString
import com.jittyandiyan.shared.demo.dataSources.apis.JsonPlaceHolderServiceAPI
import com.jittyandiyan.shared.demo.models.UserModel

class HomeViewModel(view: HomeView) : BaseViewModel<HomeView>(view) {

    companion object Bundle {
        const val USER_NAME = "USERNAME"
        const val USER_OBJECT = "USEROBJ"
    }

    override fun onStartViewModel() {
        getBundleValue<String>(USER_NAME)?.let { username ->
            getBundleValue<UserModel>(USER_OBJECT)?.let { userModel ->
                getView()?.showUsername(userModel.toJsonString(UserModel.serializer()))
            }
            runOnBackground(username) {
                JsonPlaceHolderServiceAPI()::getPosts
            }.resultOnUI {
                getView()?.showPostList(it)
            }
        }
    }
}