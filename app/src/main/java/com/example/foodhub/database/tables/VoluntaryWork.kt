package com.example.foodhub.database.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class VoluntaryWork(
    @PrimaryKey(autoGenerate = true)
    val vId: Int,

    @ColumnInfo(name = "v_image")
    val vImage: String,

    @ColumnInfo(name = "v_title")
    val vTitle: String = "",

    @ColumnInfo(name = "v_desc")
    val vDesc: String = "",

    @ColumnInfo(name = "v_street")
    val vStreet: String = "",

    @ColumnInfo(name = "v_city")
    val vCity: String = "",

    @ColumnInfo(name = "v_postcode")
    val vPostcode: String = "",

    @ColumnInfo(name = "v_state")
    val vState: String = "",

    @ColumnInfo(name = "v_country")
    val vCountry: String = "",

    @ColumnInfo(name = "v_website")
    val vWebsite: String = "",

    @ColumnInfo(name = "v_phone")
    val vPhone: String = "",

    @ColumnInfo(name = "v_reg_link")
    val vRegLink: String = "",

    @ColumnInfo(name = "v_maps")
    val vMaps: String = "",

    @ColumnInfo(name = "v_waze")
    val vWaze: String = ""
): Parcelable