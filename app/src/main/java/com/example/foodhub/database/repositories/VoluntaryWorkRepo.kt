package com.example.foodhub.database.repositories

import androidx.lifecycle.LiveData
import com.example.foodhub.database.daos.VoluntaryWorkDao
import com.example.foodhub.database.tables.VoluntaryWork

class VoluntaryWorkRepo(private val voluntaryWorkDao: VoluntaryWorkDao) {

    val getAllWork: LiveData<List<VoluntaryWork>> = voluntaryWorkDao.getAllWork()

    fun searchWorks(searchQuery: String): LiveData<List<VoluntaryWork>> {
        return voluntaryWorkDao.searchWorks(searchQuery)
    }

    fun getWorkCount(): LiveData<Int> {
        return voluntaryWorkDao.getWorkCount()
    }

    suspend fun insertWork(voluntaryWork: VoluntaryWork) {
        voluntaryWorkDao.insertWork(voluntaryWork)
    }

    suspend fun updateWork(voluntaryWork: VoluntaryWork) {
        voluntaryWorkDao.updateWork(voluntaryWork)
    }

    suspend fun deleteWork(voluntaryWork: VoluntaryWork) {
        voluntaryWorkDao.deleteWork(voluntaryWork)
    }

    suspend fun deleteAllWork() {
        voluntaryWorkDao.deleteAllWork()
    }

}