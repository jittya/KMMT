package com.kmmt.models.demo.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserModel(
    @SerialName("email")
    var email: String = "",
    @SerialName("firstname")
    var firstname: String = "",
    @SerialName("lastname")
    var lastname: String = "",
    @SerialName("password")
    var password: String = "",
    @SerialName("username")
    var username: String = ""
)