package com.jittyandiyan.shared.demo.features.home

import com.kmmt.core.architecture.presenter.BasePresenter
import com.kmmt.models.demo.domain.UserModel
import com.kmmt.common.platforms.runOnAndroid
import com.kmmt.network.apis.JsonPlaceHolderServiceAPI

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