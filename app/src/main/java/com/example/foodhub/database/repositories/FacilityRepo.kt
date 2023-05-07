package com.example.foodhub.database.repositories

import androidx.lifecycle.LiveData
import com.example.foodhub.database.daos.FacilityDao
import com.example.foodhub.database.tables.Facility

class FacilityRepo(private val facilityDao: FacilityDao) {

    val getAllFacility: LiveData<List<Facility>> = facilityDao.getAllFacility()

    fun searchFacilities(searchQuery: String): LiveData<List<Facility>> {
        return facilityDao.searchFacilities(searchQuery)
    }

    fun getFacilityCount(): LiveData<Int> {
        return facilityDao.getFacilityCount()
    }

    suspend fun insertFacility(facility: Facility) {
        facilityDao.insertFacility(facility)
    }

    suspend fun updateFacility(facility: Facility) {
        facilityDao.updateFacility(facility)
    }

    suspend fun deleteFacility(facility: Facility) {
        facilityDao.deleteFacility(facility)
    }

    suspend fun deleteAllFacility() {
        facilityDao.deleteAllFacility()
    }
}