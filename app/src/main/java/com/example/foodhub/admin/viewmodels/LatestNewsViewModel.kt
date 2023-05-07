package com.example.foodhub.admin.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.database.AppDatabase
import com.example.foodhub.database.repositories.LatestNewsRepo
import com.example.foodhub.database.tables.LatestNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LatestNewsViewModel(application: Application): AndroidViewModel(application) {

    val getAllNews: LiveData<List<LatestNews>>
    private val newsRepo: LatestNewsRepo
    private var totalNews: LiveData<Int>

    init {
        val latestNewsDao = AppDatabase.getDatabase(application).latestNewsDao()
        newsRepo = LatestNewsRepo(latestNewsDao)
        getAllNews = newsRepo.getAllNews
        totalNews = newsRepo.getNewsCount()
    }

    fun searchNews(searchQuery: String): LiveData<List<LatestNews>> {
        return newsRepo.searchNews(searchQuery)
    }

    fun getNewsCount(): LiveData<Int> {
        return totalNews
    }

    fun insertNews(latestNews: LatestNews) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.insertNews(latestNews)
        }
    }

    fun updateNews(latestNews: LatestNews) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.updateNews(latestNews)
        }
    }

    fun deleteNews(latestNews: LatestNews) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.deleteNews(latestNews)
        }
    }

    fun deleteAllNews() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.deleteAllNews()
        }
    }
}