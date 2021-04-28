package com.jittyandiyan.shared.apis

import com.jittyandiyan.shared.core.network.BaseAPI
import com.jittyandiyan.shared.models.PostModel

class JsonPlaceHolderServiceAPI : BaseAPI() {
    override val baseUrl: String
        get() = "https://jsonplaceholder.typicode.com/"

    suspend fun getPosts(postId: Int): List<PostModel> {
        return doGet {
            apiPath("comments?postId=$postId")
        }
    }

    suspend fun setPost(post: PostModel): PostModel {
        return doPost(post) {
            apiPath("comments")
        }
    }
}