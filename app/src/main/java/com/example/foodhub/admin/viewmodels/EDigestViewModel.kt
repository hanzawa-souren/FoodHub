package com.example.foodhub.admin.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.database.AppDatabase
import com.example.foodhub.database.repositories.EDigestRepo
import com.example.foodhub.database.tables.EDigest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EDigestViewModel(application: Application): AndroidViewModel(application) {

    val getAllDigest: LiveData<List<EDigest>>
    private val digestRepo: EDigestRepo
    private var totalDigest: LiveData<Int>

    init {
        val eDigestDao = AppDatabase.getDatabase(application).eDigestDao()
        digestRepo = EDigestRepo(eDigestDao)
        getAllDigest = digestRepo.getAllDigest
        totalDigest = digestRepo.getDigestCount()
    }

    fun searchDigests(searchQuery: String): LiveData<List<EDigest>> {
        return digestRepo.searchDigests(searchQuery)
    }

    fun getDigestCount(): LiveData<Int> {
        return totalDigest
    }


    fun insertDigest(eDigest: EDigest) {
        viewModelScope.launch(Dispatchers.IO) {
            digestRepo.insertDigest(eDigest)
        }
    }

    fun updateDigest(eDigest: EDigest) {
        viewModelScope.launch(Dispatchers.IO) {
            digestRepo.updateDigest(eDigest)
        }
    }

    fun deleteDigest(eDigest: EDigest) {
        viewModelScope.launch(Dispatchers.IO) {
            digestRepo.deleteDigest(eDigest)
        }
    }

    fun deleteAllDigest() {
        viewModelScope.launch(Dispatchers.IO) {
            digestRepo.deleteAllDigest()
        }
    }
}