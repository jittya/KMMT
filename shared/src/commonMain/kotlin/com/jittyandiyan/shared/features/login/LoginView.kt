package com.jittyandiyan.shared.features.login

import com.jittyandiyan.shared.core.architecture.view.BaseView

interface LoginView : BaseView {

    fun showGreetMsg(msg:String)
}