package com.example.foodhub.admin.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.database.AppDatabase
import com.example.foodhub.database.repositories.VoluntaryWorkRepo
import com.example.foodhub.database.tables.VoluntaryWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VoluntaryWorkViewModel(application: Application): AndroidViewModel(application) {

    val getAllWork: LiveData<List<VoluntaryWork>>
    private val workRepo: VoluntaryWorkRepo
    private var totalWork: LiveData<Int>

    init {
        val voluntaryWorkDao = AppDatabase.getDatabase(application).voluntaryWorkDao()
        workRepo = VoluntaryWorkRepo(voluntaryWorkDao)
        getAllWork = workRepo.getAllWork
        totalWork = workRepo.getWorkCount()
    }

    fun searchWorks(searchQuery: String): LiveData<List<VoluntaryWork>> {
        return workRepo.searchWorks(searchQuery)
    }

    fun getWorkCount(): LiveData<Int> {
        return totalWork
    }

    fun insertWork(voluntaryWork: VoluntaryWork) {
        viewModelScope.launch(Dispatchers.IO) {
            workRepo.insertWork(voluntaryWork)
        }
    }

    fun updateWork(voluntaryWork: VoluntaryWork) {
        viewModelScope.launch(Dispatchers.IO) {
            workRepo.updateWork(voluntaryWork)
        }
    }

    fun deleteWork(voluntaryWork: VoluntaryWork) {
        viewModelScope.launch(Dispatchers.IO) {
            workRepo.deleteWork(voluntaryWork)
        }
    }

    fun deleteAllWork() {
        viewModelScope.launch(Dispatchers.IO) {
            workRepo.deleteAllWork()
        }
    }

}