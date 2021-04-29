package com.jittyandiyan.shared.features.home

import com.jittyandiyan.shared.core.architecture.view.BaseView
import com.jittyandiyan.shared.models.PostModel

interface HomeView:BaseView {
    fun showPostList(postList:List<PostModel>)
    fun showUsername(username: String)

}