package com.example.foodhub.database.serializable

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerialVoluntaryWork(
    @SerialName("vId")
    val vId: Int,

    @SerialName("vImage")
    val vImage: String,

    @SerialName("vTitle")
    val vTitle: String,

    @SerialName("vDesc")
    val vDesc: String,

    @SerialName("vStreet")
    val vStreet: String,

    @SerialName("vCity")
    val vCity: String,

    @SerialName("vPostcode")
    val vPostcode: String,

    @SerialName("vState")
    val vState: String,

    @SerialName("vCountry")
    val vCountry: String,

    @SerialName("vWebsite")
    val vWebsite: String,

    @SerialName("vPhone")
    val vPhone: String,

    @SerialName("vRegLink")
    val vRegLink: String,

    @SerialName("vMaps")
    val vMaps: String,

    @SerialName("vWaze")
    val vWaze: String
)