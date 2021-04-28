package com.jittyandiyan.shared.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PostModel(
    @SerialName("body")
    var body: String? = null,
    @SerialName("email")
    var email: String? = null,
    @SerialName("id")
    var id: Int? = null,
    @SerialName("name")
    var name: String? = null,
    @SerialName("postId")
    var postId: Int? = null
)