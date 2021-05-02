package com.jittyandiyan.shared.demo.models.Breed

import kotlinx.serialization.Serializable

@Serializable
data class BreedResult(
    val message: Map<String, List<String>>,
    var status: String
)

