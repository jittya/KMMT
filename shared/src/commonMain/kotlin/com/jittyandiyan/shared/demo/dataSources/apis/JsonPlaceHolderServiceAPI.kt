package com.jittyandiyan.shared.demo.dataSources.apis

import com.jittyandiyan.shared.core.network.BaseAPI
import com.jittyandiyan.shared.demo.models.CredentialsModel
import com.jittyandiyan.shared.demo.models.PostModel
import com.jittyandiyan.shared.demo.models.UserModel

class JsonPlaceHolderServiceAPI : BaseAPI() {
    override val baseUrl: String
        get() = "https://my-json-server.typicode.com/"

    suspend fun authenticate(credentails:CredentialsModel):Boolean
    {
        var userData:List<UserModel> = doGet {
            apiPath("jittya/jsonserver/users?username=${credentails.username}&password=${credentails.password}")
        }
        println()
        return userData.any { it.username == credentails.username && it.password == credentails.password }

    }

    suspend fun getPosts(username: String): List<PostModel> {
        return doGet {
            apiPath("jittya/jsonserver/post?username=$username")
        }
    }

    //Example POST Method
    private suspend fun setPost(post: PostModel): PostModel {
        return doPost(post) {
            apiPath("comments")
        }
    }
}