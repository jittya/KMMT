package com.jittyandiyan.shared.demo.features.home

import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel
import com.jittyandiyan.shared.core.platform.runOnAndroid
import com.jittyandiyan.shared.demo.dataSources.apis.JsonPlaceHolderServiceAPI
import com.jittyandiyan.shared.demo.models.UserModel

class HomeViewModel(view: HomeView) : BaseViewModel<HomeView>(view) {

    companion object BundleKeys {
        const val USER_NAME = "USERNAME"
        const val USER_OBJECT = "USEROBJ"
    }

    override fun onStartViewModel() {
        runOnAndroid {
            getView()?.setPageTitle("KMM : Home")
        }
        getView()?.setKampKitPageButtonLabel("KampKit Demo")
        getView()?.setKampKitBtnClickAction(this::kampKitDemoBtnClicked)
        getBundleValue<String>(USER_NAME)?.let { username ->
            getBundleValue<UserModel>(USER_OBJECT)?.let { userModel ->
                getView()?.showUsername(" User : " + userModel.firstname + " " + userModel.lastname)
            }
            runOnBackgroundAsFlow {
                JsonPlaceHolderServiceAPI().getPosts(username)
            }.resultOnUI {
                it.either({
                    getView()?.showPopUpMessage(it.message)
                }, {
                    getView()?.showPostList(it)
                })

            }
        }
    }

    fun kampKitDemoBtnClicked() {
        getView()?.navigateToKampKitDemoPage()
    }
}