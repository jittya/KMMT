package com.jittyandiyan.shared.core.network.APIs

import com.jittyandiyan.shared.core.network.HTTPHelper
import com.jittyandiyan.shared.models.ProfileModel
import kotlinx.coroutines.delay

class ProfileMicroServiceV1API :BaseAPI(){



    suspend fun getJsonFromApi(): ProfileModel {
        return HTTPHelper().doGet<ProfileModel> {
            apiPath("v1/e2c58213-cd6a-4e18-a170-83daf39b2f6c")
        }
    }

    suspend fun getNames(name:Int):String
    {
        delay(5000)
        return name.toString()
    }

    override val baseUrl: String
        get() = "https://mocki.io/"
}