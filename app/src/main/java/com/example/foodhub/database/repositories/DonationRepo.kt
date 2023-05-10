package com.example.foodhub.database.repositories

import androidx.lifecycle.LiveData
import com.example.foodhub.database.daos.DonationDao

class DonationRepo(private val donationDao: DonationDao) {

    fun getTotalDonationAmount(): LiveData<Double> {
        return donationDao.getTotalDonatedAmount()
    }
    fun getDonationCount():LiveData<Int>{
        return donationDao.getDonationCount()
    }
}