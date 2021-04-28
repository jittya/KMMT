package com.jittyandiyan.shared.apis

import com.jittyandiyan.shared.core.network.BaseAPI
import com.jittyandiyan.shared.models.CredentialsModel
import com.jittyandiyan.shared.models.PostModel
import com.jittyandiyan.shared.models.UserModel
import kotlinx.coroutines.delay

class JsonPlaceHolderServiceAPI : BaseAPI() {
    override val baseUrl: String
        get() = "https://my-json-server.typicode.com/"

    suspend fun authenticate(credentails:CredentialsModel):Boolean
    {
        delay(1000)
        var userData:List<UserModel> = doGet {
            apiPath("jittya/jsonserver/users?username=${credentails.username}&password=${credentails.password}")
        }
        println()
        return userData.any { it.username == credentails.username && it.password == credentails.password }

    }

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