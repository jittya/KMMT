package com.kmmt.models.demo.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PostModel(
    @SerialName("username")
    var username: String = "",
    @SerialName("body")
    var body: String? = null,
    @SerialName("email")
    var email: String? = null,
    @SerialName("id")
    var id: Int,
    @SerialName("name")
    var name: String? = null,
    @SerialName("postId")
    var postId: Int? = null
)