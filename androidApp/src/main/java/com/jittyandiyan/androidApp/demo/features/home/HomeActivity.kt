package com.jittyandiyan.androidApp.demo.features.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jittyandiyan.androidApp.databinding.ActivityHomeBinding
import com.jittyandiyan.androidApp.demo.features.home.adapter.PostAdapter
import com.jittyandiyan.shared.core.architecture.view.KMMActivity
import com.jittyandiyan.shared.demo.features.home.HomeView
import com.jittyandiyan.shared.demo.features.home.HomeViewModel
import com.jittyandiyan.shared.demo.models.PostModel

class HomeActivity : KMMActivity<HomeViewModel, ActivityHomeBinding>(), HomeView {

    private lateinit var adapter: PostAdapter

    override fun initializeViewModel(): HomeViewModel {
        return HomeViewModel(this)
    }

    override fun viewBindingInflate(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = PostAdapter()
        binding.postListRV.adapter=adapter
        binding.postListRV.layoutManager=LinearLayoutManager(this)
    }

    override fun showPostList(postList: List<PostModel>) {
        adapter.submitList(postList)
    }

    override fun showUsername(username: String) {
        binding.usernameTV.text=username
    }

}