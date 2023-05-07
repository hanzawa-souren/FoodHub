package com.example.foodhub.database.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Facility(
    @PrimaryKey(autoGenerate = true)
    val nId: Int,

    @ColumnInfo(name = "n_image")
    val nImage: String = "",

    @ColumnInfo(name = "n_name")
    val nName: String = "",

    @ColumnInfo(name = "n_facility")
    val nFacility: String = "",

    @ColumnInfo(name = "n_desc")
    val nDesc: String = "",

    @ColumnInfo(name = "n_street")
    val nStreet: String = "",

    @ColumnInfo(name = "n_city")
    val nCity: String = "",

    @ColumnInfo(name = "n_postcode")
    val nPostcode: String = "",

    @ColumnInfo(name = "n_state")
    val nState: String = "",

    @ColumnInfo(name = "n_country")
    val nCountry: String = "",

    @ColumnInfo(name = "n_website")
    val nWebsite: String = "",

    @ColumnInfo(name = "n_phone")
    val nPhone: String = "",

    @ColumnInfo(name = "n_maps")
    val nMaps: String = "",

    @ColumnInfo(name = "n_waze")
    val nWaze: String = ""
): Parcelable