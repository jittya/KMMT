package com.jittyandiyan.shared.core.architecture.view


interface BaseView{
    fun showPopUpMessage(message: String)
    fun showPopUpMessage(title:String,message: String)
    fun showLoading(loadingLabel:String)
    fun dismissLoading()
    fun setPageTitle(title: String)
}
