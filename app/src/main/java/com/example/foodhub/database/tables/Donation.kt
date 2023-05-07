package com.example.foodhub.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Donation(
    @PrimaryKey(autoGenerate = true)
    val dId: Int,

    @ColumnInfo(name = "u_id")
    val uId: String = "",

    @ColumnInfo(name = "d_method")
    val dMethod: String = "",

    @ColumnInfo(name = "d_amount")
    val dAmount: Double = 0.0
)