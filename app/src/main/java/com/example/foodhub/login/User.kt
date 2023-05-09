package com.example.foodhub.login


import androidx.room.ColumnInfo

import java.util.*

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val loginID : String,

    val password : String,
    @ColumnInfo(name = "day") val day: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    @ColumnInfo(name = "month") val month: Int = Calendar.getInstance().get(Calendar.MONTH) ,
    @ColumnInfo(name = "year") val year: Int = Calendar.getInstance().get(Calendar.YEAR)
): Parcelable

