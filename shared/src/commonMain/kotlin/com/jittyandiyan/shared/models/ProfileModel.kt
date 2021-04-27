package com.jittyandiyan.shared.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel(
    @SerialName("github")
    var github: String? = null,
    @SerialName("name")
    var name: String? = null
)