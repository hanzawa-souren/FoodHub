package com.example.foodhub.admin.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.database.AppDatabase
import com.example.foodhub.database.repositories.HelplineRepo
import com.example.foodhub.database.tables.Helpline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HelplineViewModel(application: Application): AndroidViewModel(application) {

    val getAllHelpline: LiveData<List<Helpline>>
    private val helplineRepo: HelplineRepo
    private var totalHelpline: LiveData<Int>

    init {
        val helplineDao = AppDatabase.getDatabase(application).helplineDao()
        helplineRepo = HelplineRepo(helplineDao)
        getAllHelpline = helplineRepo.getAllHelpline
        totalHelpline = helplineRepo.getHelplineCount()
    }

    fun searchHelplines(searchQuery: String): LiveData<List<Helpline>> {
        return helplineRepo.searchHelplines(searchQuery)
    }

    fun getHelplineCount(): LiveData<Int> {
        return totalHelpline
    }

    fun insertHelpline(helpline: Helpline) {
        viewModelScope.launch(Dispatchers.IO) {
            helplineRepo.insertHelpline(helpline)
        }
    }

    fun updateHelpline(helpline: Helpline) {
        viewModelScope.launch(Dispatchers.IO) {
            helplineRepo.updateHelpline(helpline)
        }
    }

    fun deleteHelpline(helpline: Helpline) {
        viewModelScope.launch(Dispatchers.IO) {
            helplineRepo.deleteHelpline(helpline)
        }
    }

    fun deleteAllHelpline() {
        viewModelScope.launch(Dispatchers.IO) {
            helplineRepo.deleteAllHelpline()
        }
    }
}