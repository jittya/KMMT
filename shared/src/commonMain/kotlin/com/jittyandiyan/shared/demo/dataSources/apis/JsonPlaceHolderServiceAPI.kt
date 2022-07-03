package com.jittyandiyan.shared.demo.dataSources.apis

import com.kmmt.core.functional.Either
import com.kmmt.core.functional.flatMap
import com.kmmt.core.models.NetworkFailure
import com.kmmt.core.network.BaseAPI
import com.jittyandiyan.shared.demo.models.CredentialsModel
import com.jittyandiyan.shared.demo.models.PostModel
import com.jittyandiyan.shared.demo.models.UserModel

class JsonPlaceHolderServiceAPI : BaseAPI() {
    override val baseUrl: String
        get() = "https://my-json-server.typicode.com/"

    suspend fun authenticate(credentails: CredentialsModel): Either<Boolean, NetworkFailure> {
        var result = doGet<List<UserModel>>("jittya/jsonserver/users?username=${credentails.username}&password=${credentails.password}") {
        }

        return result.flatMap {
            Either.Success(it.any { it.username == credentails.username && it.password == credentails.password })
        }

    }

    suspend fun getPosts(username: String): Either<List<PostModel>, NetworkFailure> {
        return doGet ("jittya/jsonserver/post?username=$username"){
        }
    }

    //Example POST Method
    private suspend fun setPost(post: PostModel): Either<PostModel, NetworkFailure> {
        return doPost("comments",post)
    }
}