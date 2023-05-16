package com.example.foodhub.database.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "volunteered_work")
data class UserVolunteeredWork(
    @PrimaryKey(autoGenerate = true)
    val uvwId: Int,
    @ColumnInfo(name = "v_id")
    val vId: Int,
    @ColumnInfo(name = "u_id")
    val uId: String = "",

    @ColumnInfo(name = "status")
    val status: String = "Pending",

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
    val vWaze: String = "",
    @ColumnInfo(name = "day") val day: Int ,
    @ColumnInfo(name = "month") val month: Int

)