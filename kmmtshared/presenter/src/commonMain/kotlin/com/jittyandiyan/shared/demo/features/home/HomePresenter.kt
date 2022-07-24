package com.jittyandiyan.shared.demo.features.home

import com.kmmt.core.architecture.presenter.BasePresenter
import com.kmmt.models.demo.domain.UserModel
import com.kmmt.common.platforms.runOnAndroid
import com.kmmt.network.apis.JsonPlaceHolderServiceAPI
import com.kmmt.resources.Resources
import com.kmmt.resources.expectations.localized

class HomePresenter(view: HomeView) : BasePresenter<HomeView>(view) {

    companion object BundleKeys {
        const val USER_NAME = "USERNAME"
        const val USER_OBJECT = "USEROBJ"
    }

    override fun onStartPresenter() {
        runOnAndroid {
            getView()?.setPageTitle(Resources.strings.kmmHome.localized())
        }
        getView()?.setKampKitPageButtonLabel(Resources.strings.kampKitDemo.localized())
        getView()?.setKampKitBtnClickAction(this::kampKitDemoBtnClicked)
        getBundleValue<String>(USER_NAME)?.let { username ->
            getBundleValue<UserModel>(USER_OBJECT)?.let { userModel ->
                getView()?.showUsername(" ${Resources.strings.user.localized()} : " + userModel.firstname + " " + userModel.lastname)
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