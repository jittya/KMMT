package com.jittyandiyan.androidApp.demo.features.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jittyandiyan.androidApp.databinding.ActivityHomeBinding
import com.jittyandiyan.androidApp.demo.features.home.adapter.PostAdapter
import com.jittyandiyan.androidApp.demo.features.kampkit.BreedActivity
import com.kmmt.core.architecture.view.KMMActivity
import com.jittyandiyan.shared.demo.features.home.HomeView
import com.jittyandiyan.shared.demo.features.home.HomePresenter
import com.kmmt.models.demo.domain.PostModel
import kotlin.reflect.KFunction0

class HomeActivity : KMMActivity<HomePresenter, ActivityHomeBinding>(), HomeView {

    private lateinit var adapter: PostAdapter

    override fun initializePresenter(): HomePresenter {
        return HomePresenter(this)
    }

    override fun viewBindingInflate(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = PostAdapter()
        binding.postListRV.adapter = adapter
        binding.postListRV.layoutManager = LinearLayoutManager(this)
    }

    override fun showPostList(postList: List<PostModel>) {
        adapter.submitList(postList)
    }

    override fun showUsername(username: String) {
        binding.usernameTV.text = username
    }

    override fun setKampKitPageButtonLabel(btnLabel: String) {
        binding.kampKitBtn.text = btnLabel
    }

    override fun navigateToKampKitDemoPage() {
        openActivity(BreedActivity::class.java)
    }

    override fun setKampKitBtnClickAction(btnClickAction: KFunction0<Unit>) {
        binding.kampKitBtn.setOnClickListener { btnClickAction.invoke() }
    }

}