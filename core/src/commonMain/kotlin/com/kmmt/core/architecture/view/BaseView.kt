package com.kmmt.core.architecture.view

import dev.icerock.moko.resources.StringResource


interface BaseView{
    fun showPopUpMessage(message: String)
    fun showPopUpMessage(title:String,message: String)
    fun showLoading(loadingLabel:String)
    fun dismissLoading()
    fun setPageTitle(title: String)
}
