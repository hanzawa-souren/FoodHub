package com.example.foodhub.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.foodhub.database.tables.Donation

@Dao
interface DonationDao {
    @Query("SELECT * FROM donation")
    fun getAllDonation(): LiveData<List<Donation>>

    @Query("SELECT SUM(d_amount) FROM donation")
    fun getTotalDonatedAmount(): LiveData<Double>




    
}