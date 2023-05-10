package com.example.foodhub.admin.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.database.AppDatabase
import com.example.foodhub.database.repositories.DonationRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DonationViewModel(application: Application): AndroidViewModel(application) {

    private var totalAmount: LiveData<Double>
    private val donationRepo: DonationRepo
    private var amountCount: LiveData<Int>
    init {
        val donationDao = AppDatabase.getDatabase(application).donationDao()
        donationRepo = DonationRepo(donationDao)
        totalAmount = donationRepo.getTotalDonationAmount()
        amountCount = donationDao.getDonationCount()
    }

    fun getTotalDonationAmount(): LiveData<Double> {
        return totalAmount
    }

    fun getDonationCount():LiveData<Int>{

        return amountCount
    }
}