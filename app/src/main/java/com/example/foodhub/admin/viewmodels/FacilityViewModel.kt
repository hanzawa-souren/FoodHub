package com.example.foodhub.admin.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.database.AppDatabase
import com.example.foodhub.database.repositories.FacilityRepo
import com.example.foodhub.database.tables.Facility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FacilityViewModel(application: Application): AndroidViewModel(application) {

    val getAllFacility: LiveData<List<Facility>>
    private val facilityRepo: FacilityRepo
    private var totalFacility: LiveData<Int>

    init {
        val facilityDao = AppDatabase.getDatabase(application).facilityDao()
        facilityRepo = FacilityRepo(facilityDao)
        getAllFacility = facilityRepo.getAllFacility
        totalFacility = facilityRepo.getFacilityCount()
    }

    fun searchFacilities(searchQuery: String): LiveData<List<Facility>> {
        return facilityRepo.searchFacilities(searchQuery)
    }

    fun getFacilityCount(): LiveData<Int> {
        return totalFacility
    }

    fun insertFacility(facility: Facility) {
        viewModelScope.launch(Dispatchers.IO) {
            facilityRepo.insertFacility(facility)
        }
    }

    fun updateFacility(facility: Facility) {
        viewModelScope.launch(Dispatchers.IO) {
            facilityRepo.updateFacility(facility)
        }
    }

    fun deleteFacility(facility: Facility) {
        viewModelScope.launch(Dispatchers.IO) {
            facilityRepo.deleteFacility(facility)
        }
    }

    fun deleteAllFacility() {
        viewModelScope.launch(Dispatchers.IO) {
            facilityRepo.deleteAllFacility()
        }
    }
}