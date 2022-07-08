package com.kmmt.models.demotvshowsearch.dto
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonObject


@Serializable
data class TVShowsDTOItem(
    @SerialName("score")
    val score: Double? = null,
    @SerialName("show")
    val show: Show
)

@Serializable
data class Show(
    @SerialName("averageRuntime")
    val averageRuntime: Int? = null,
    @SerialName("dvdCountry")
    val dvdCountry: String? = null,
    @SerialName("ended")
    val ended: String? = null,
    @SerialName("externals")
    val externals: Externals? = null,
    @SerialName("genres")
    val genres: List<String>? = null,
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: Image? = null,
    @SerialName("language")
    val language: String? = null,
    @SerialName("_links")
    val links: Links? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("network")
    val network: Network? = null,
    @SerialName("officialSite")
    val officialSite: String? = null,
    @SerialName("premiered")
    val premiered: String? = null,
    @SerialName("rating")
    val rating: Rating? = null,
    @SerialName("runtime")
    val runtime: Int? = null,
    @SerialName("schedule")
    val schedule: Schedule? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("summary")
    val summary: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("updated")
    val updated: Int? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("webChannel")
    val webChannel: JsonObject? = null,
    @SerialName("weight")
    val weight: Int? = null
)

@Serializable
data class Externals(
    @SerialName("imdb")
    val imdb: String? = null,
    @SerialName("thetvdb")
    val thetvdb: Int? = null,
    @SerialName("tvrage")
    val tvrage: String? = null
)

@Serializable
data class Image(
    @SerialName("medium")
    val medium: String? = null,
    @SerialName("original")
    val original: String? = null
)

@Serializable
data class Links(
    @SerialName("previousepisode")
    val previousepisode: Previousepisode? = null,
    @SerialName("self")
    val self: Self? = null
)

@Serializable
data class Network(
    @SerialName("country")
    val country: Country? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null
)

@Serializable
data class Rating(
    @SerialName("average")
    val average: Double? = null
)

@Serializable
data class Schedule(
    @SerialName("days")
    val days: List<String>? = null,
    @SerialName("time")
    val time: String? = null
)

@Serializable
data class Previousepisode(
    @SerialName("href")
    val href: String? = null
)

@Serializable
data class Self(
    @SerialName("href")
    val href: String? = null
)

@Serializable
data class Country(
    @SerialName("code")
    val code: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("timezone")
    val timezone: String? = null
)