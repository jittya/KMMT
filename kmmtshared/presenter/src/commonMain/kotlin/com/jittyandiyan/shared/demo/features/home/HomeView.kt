package com.jittyandiyan.shared.demo.features.home

import com.kmmt.core.architecture.view.BaseView
import com.kmmt.models.demo.domain.PostModel
import kotlin.reflect.KFunction0

interface HomeView:BaseView {
    fun showPostList(postList:List<PostModel>)
    fun showUsername(username: String)
    fun setKampKitPageButtonLabel(btnLabel: String)
    fun navigateToKampKitDemoPage()
    fun setKampKitBtnClickAction(btnClickAction: KFunction0<Unit>)

}