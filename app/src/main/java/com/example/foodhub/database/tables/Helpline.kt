package com.example.foodhub.database.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Helpline(
    @PrimaryKey(autoGenerate = true)
    val hId: Int,

    @ColumnInfo(name = "h_title")
    val hTitle: String = "",

    @ColumnInfo(name = "h_phone")
    val hPhone: String = "",

    @ColumnInfo(name = "h_website")
    val hWebsite: String = "",

    @ColumnInfo(name = "h_desc")
    val hDesc: String = ""
): Parcelable