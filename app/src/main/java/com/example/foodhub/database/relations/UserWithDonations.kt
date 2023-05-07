package com.example.foodhub.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.foodhub.database.tables.Donation
import com.example.foodhub.login.User

data class UserWithDonations(
    @Embedded val user: User,
    @Relation(
        parentColumn = "loginID",
        entityColumn = "u_id"
    )
    val donations: List<Donation>
)