package com.example.foodhub.database.repositories

import androidx.lifecycle.LiveData
import com.example.foodhub.database.daos.EDigestDao
import com.example.foodhub.database.tables.EDigest

class EDigestRepo(private val eDigestDao: EDigestDao) {

    val getAllDigest: LiveData<List<EDigest>> = eDigestDao.getAllDigest()

    fun searchDigests(searchQuery: String): LiveData<List<EDigest>> {
        return eDigestDao.searchDigests(searchQuery)
    }

    fun getDigestCount(): LiveData<Int> {
        return eDigestDao.getDigestCount()
    }

    suspend fun insertDigest(eDigest: EDigest) {
        eDigestDao.insertDigest(eDigest)
    }

    suspend fun updateDigest(eDigest: EDigest) {
        eDigestDao.updateDigest(eDigest)
    }

    suspend fun deleteDigest(eDigest: EDigest) {
        eDigestDao.deleteDigest(eDigest)
    }

    suspend fun deleteAllDigest() {
        eDigestDao.deleteAllDigest()
    }
}