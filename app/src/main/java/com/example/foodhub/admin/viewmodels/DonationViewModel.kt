package com.example.foodhub.admin.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.foodhub.database.AppDatabase
import com.example.foodhub.database.repositories.DonationRepo

class DonationViewModel(application: Application): AndroidViewModel(application) {

    private var totalAmount: LiveData<Double>
    private val donationRepo: DonationRepo

    init {
        val donationDao = AppDatabase.getDatabase(application).donationDao()
        donationRepo = DonationRepo(donationDao)
        totalAmount = donationRepo.getTotalDonationAmount()
    }

    fun getTotalDonationAmount(): LiveData<Double> {
        return totalAmount
    }
}