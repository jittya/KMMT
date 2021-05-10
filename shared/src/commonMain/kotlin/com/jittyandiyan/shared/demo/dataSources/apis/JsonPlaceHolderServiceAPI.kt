package com.jittyandiyan.shared.demo.dataSources.apis

import com.jittyandiyan.shared.core.functional.Either
import com.jittyandiyan.shared.core.functional.flatMap
import com.jittyandiyan.shared.core.models.NetworkFailure
import com.jittyandiyan.shared.core.network.BaseAPI
import com.jittyandiyan.shared.demo.models.CredentialsModel
import com.jittyandiyan.shared.demo.models.PostModel
import com.jittyandiyan.shared.demo.models.UserModel

class JsonPlaceHolderServiceAPI : BaseAPI() {
    override val baseUrl: String
        get() = "https://my-json-server.typicode.com/"

    suspend fun authenticate(credentails: CredentialsModel): Either<Boolean, NetworkFailure> {
        var result = doGet<List<UserModel>> {
            apiPath("jittya/jsonserver/users?username=${credentails.username}&password=${credentails.password}")
        }

        return result.flatMap {
            Either.Success(it.any { it.username == credentails.username && it.password == credentails.password })
        }

    }

    suspend fun getPosts(username: String): Either<List<PostModel>, NetworkFailure> {
        return doGet {
            apiPath("jittya/jsonserver/post?username=$username")
        }
    }

    //Example POST Method
    private suspend fun setPost(post: PostModel): Either<PostModel, NetworkFailure> {
        return doPost(post) {
            apiPath("comments")
        }
    }
}