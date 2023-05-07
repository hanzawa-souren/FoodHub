package com.example.foodhub.database.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class EDigest(
    @PrimaryKey(autoGenerate = true)
    val eId: Int,

    @ColumnInfo(name = "e_image")
    val eImage: String,

    @ColumnInfo(name = "e_author")
    val eAuthor: String = "",

    @ColumnInfo(name = "e_title")
    val eTitle: String = "",

    @ColumnInfo(name = "e_date")
    val eDate: String = "",

    @ColumnInfo(name = "e_content")
    val eContent: String = ""
): Parcelable