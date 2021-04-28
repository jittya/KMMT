package com.jittyandiyan.androidApp.features

import android.content.Context
import android.content.Intent
import com.jittyandiyan.androidApp.databinding.ActivityHomeBinding
import com.jittyandiyan.shared.core.architecture.view.KMMActivity
import com.jittyandiyan.shared.features.home.HomeView
import com.jittyandiyan.shared.features.home.HomeViewModel

class HomeActivity : KMMActivity<HomeViewModel, ActivityHomeBinding>(), HomeView {

    companion object {
        fun Context.openHomeActivity(username: String) {
            var intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }
    }

    override fun initializeViewModel(): HomeViewModel {
        return HomeViewModel(this)
    }

    override fun viewBindingInflate(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }
}