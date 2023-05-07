package com.example.foodhub.database.repositories

import androidx.lifecycle.LiveData
import com.example.foodhub.database.daos.LatestNewsDao
import com.example.foodhub.database.tables.LatestNews

class LatestNewsRepo(private val latestNewsDao: LatestNewsDao) {

    val getAllNews: LiveData<List<LatestNews>> = latestNewsDao.getAllNews()

    fun searchNews(searchQuery: String): LiveData<List<LatestNews>> {
        return latestNewsDao.searchNews(searchQuery)
    }

    fun getNewsCount(): LiveData<Int> {
        return latestNewsDao.getNewsCount()
    }

    suspend fun insertNews(latestNews: LatestNews) {
        latestNewsDao.insertNews(latestNews)
    }

    suspend fun updateNews(latestNews: LatestNews) {
        latestNewsDao.updateNews(latestNews)
    }

    suspend fun deleteNews(latestNews: LatestNews) {
        latestNewsDao.deleteNews(latestNews)
    }

    suspend fun deleteAllNews() {
        latestNewsDao.deleteAllNews()
    }
}