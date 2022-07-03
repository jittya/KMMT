package com.jittyandiyan.shared.demo.features.home

import com.kmmt.core.architecture.presenter.BasePresenter
import com.kmmt.core.platform.runOnAndroid
import com.jittyandiyan.shared.demo.dataSources.apis.JsonPlaceHolderServiceAPI
import com.jittyandiyan.shared.demo.models.UserModel

class HomePresenter(view: HomeView) : BasePresenter<HomeView>(view) {

    companion object BundleKeys {
        const val USER_NAME = "USERNAME"
        const val USER_OBJECT = "USEROBJ"
    }

    override fun onStartPresenter() {
        runOnAndroid {
            getView()?.setPageTitle("KMM : Home")
        }
        getView()?.setKampKitPageButtonLabel("KampKit Demo")
        getView()?.setKampKitBtnClickAction(this::kampKitDemoBtnClicked)
        getBundleValue<String>(USER_NAME)?.let { username ->
            getBundleValue<UserModel>(USER_OBJECT)?.let { userModel ->
                getView()?.showUsername(" User : " + userModel.firstname + " " + userModel.lastname)
            }
            runOnBackgroundWithResult {
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