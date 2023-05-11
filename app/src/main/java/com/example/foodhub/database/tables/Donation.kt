package com.example.foodhub.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Donation(
    @PrimaryKey(autoGenerate = true)
    val dId: Int,

    @ColumnInfo(name = "u_id")
    val uId: String = "",

    @ColumnInfo(name = "d_method")
    val dMethod: String = "",

    @ColumnInfo(name = "d_amount")
    val dAmount: Double = 0.0,
    @ColumnInfo(name = "d_cardNumber")
    val cardNumber: String,
    @ColumnInfo(name = "day") val day: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    @ColumnInfo(name = "month") val month: Int = Calendar.getInstance().get(Calendar.MONTH) ,
    @ColumnInfo(name = "year") val year: Int = Calendar.getInstance().get(Calendar.YEAR)
)