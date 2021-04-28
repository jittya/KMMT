package com.jittyandiyan.shared.apis

import com.jittyandiyan.shared.core.network.BaseAPI
import com.jittyandiyan.shared.core.network.HTTPHelper
import com.jittyandiyan.shared.models.ProfileModel


class ProfileMicroServiceAPI : BaseAPI() {

    override val baseUrl: String
        get() = "https://mocki.io/"

    suspend fun getProfile(): ProfileModel {
        return HTTPHelper().doGet<ProfileModel> {
            apiPath("v1/e2c58213-cd6a-4e18-a170-83daf39b2f6c")
        }
    }

}