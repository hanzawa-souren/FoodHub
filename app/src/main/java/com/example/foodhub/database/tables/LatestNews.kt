package com.example.foodhub.database.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class LatestNews(
    @PrimaryKey(autoGenerate = true)
    val lnId: Int,

    @ColumnInfo(name = "ln_image")
    val lnImage: String,

    @ColumnInfo(name = "ln_author")
    val lnAuthor: String = "",

    @ColumnInfo(name = "ln_title")
    val lnTitle: String = "",

    @ColumnInfo(name = "ln_date")
    val lnDate: String = "",

    @ColumnInfo(name = "ln_content")
    val lnContent: String = ""
): Parcelable