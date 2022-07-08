package com.kmmt.network.apis

import com.kmmt.common.functional.Either
import com.kmmt.common.functional.flatMap
import com.kmmt.common.models.NetworkFailure
import com.kmmt.models.demo.domain.CredentialsModel
import com.kmmt.models.demo.domain.PostModel
import com.kmmt.models.demo.domain.UserModel
import com.kmmt.network.core.BaseAPI

class JsonPlaceHolderServiceAPI : BaseAPI() {
    override val baseUrl: String
        get() = "https://my-json-server.typicode.com/"

    suspend fun authenticate(credentails: CredentialsModel): Either<Boolean, NetworkFailure> {
        var result =
            doGet<List<UserModel>>("jittya/jsonserver/users?username=${credentails.username}&password=${credentails.password}")

        return result.flatMap {
            Either.Success(it.any { it.username == credentails.username && it.password == credentails.password })
        }

    }

    suspend fun getPosts(username: String): Either<List<PostModel>, NetworkFailure> {
        return doGet("jittya/jsonserver/post?username=$username")
    }

    //Example POST Method
    private suspend fun setPost(post: PostModel): Either<PostModel, NetworkFailure> {
        return doPost("comments", post)
    }
}