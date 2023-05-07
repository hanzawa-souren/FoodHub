package com.example.foodhub.database.repositories

import androidx.lifecycle.LiveData
import com.example.foodhub.database.daos.HelplineDao
import com.example.foodhub.database.tables.Helpline

class HelplineRepo(private val helplineDao: HelplineDao) {

    val getAllHelpline: LiveData<List<Helpline>> = helplineDao.getAllHelpline()

    fun searchHelplines(searchQuery: String): LiveData<List<Helpline>> {
        return helplineDao.searchHelplines(searchQuery)
    }

    fun getHelplineCount(): LiveData<Int> {
        return helplineDao.getHelplineCount()
    }

    suspend fun insertHelpline(helpline: Helpline) {
        helplineDao.insertHelpline(helpline)
    }

    suspend fun updateHelpline(helpline: Helpline) {
        helplineDao.updateHelpline(helpline)
    }

    suspend fun deleteHelpline(helpline: Helpline) {
        helplineDao.deleteHelpline(helpline)
    }

    suspend fun deleteAllHelpline() {
        helplineDao.deleteAllHelpline()
    }
}