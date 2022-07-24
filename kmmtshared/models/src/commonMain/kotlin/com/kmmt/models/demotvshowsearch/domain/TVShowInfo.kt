package com.kmmt.models.demotvshowsearch.domain

data class TVShowInfo(
    val id:Long,
    var name:String?,
    val language: String?,
    val image:String?,
    val summary:String?
)