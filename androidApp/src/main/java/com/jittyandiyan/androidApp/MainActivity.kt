package com.jittyandiyan.androidApp

import android.os.Bundle
import com.jittyandiyan.androidApp.databinding.ActivityMainBinding
import com.jittyandiyan.shared.core.architecture.view.KMMActivity
import com.jittyandiyan.shared.features.login.LoginView
import com.jittyandiyan.shared.features.login.LoginViewModel

class MainActivity : KMMActivity<LoginViewModel>(), LoginView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private lateinit var binding: ActivityMainBinding

    //Generated Methods from LoginView
    override fun showGreetMsg(msg: String) {
        binding.textView.text = msg
    }

    //Generated Methods from LoginViewModel
    override fun initializeViewModel(): LoginViewModel {
        return LoginViewModel(this)
    }


}
