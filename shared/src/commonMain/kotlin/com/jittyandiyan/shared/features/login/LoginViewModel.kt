package com.jittyandiyan.shared.features.login

import com.jittyandiyan.shared.Greeting
import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel

class LoginViewModel(view: LoginView) :BaseViewModel<LoginView>(view) {
    override fun onStartViewModel() {
        getView()?.showGreetMsg(Greeting().greeting())
    }
}