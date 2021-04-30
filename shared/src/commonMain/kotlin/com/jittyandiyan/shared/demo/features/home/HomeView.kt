package com.jittyandiyan.shared.demo.features.home

import com.jittyandiyan.shared.core.architecture.view.BaseView
import com.jittyandiyan.shared.demo.models.PostModel

interface HomeView:BaseView {
    fun showPostList(postList:List<PostModel>)
    fun showUsername(username: String)

}