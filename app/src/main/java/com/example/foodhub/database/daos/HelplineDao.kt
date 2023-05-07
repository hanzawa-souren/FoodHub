package com.example.foodhub.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodhub.database.tables.Helpline

@Dao
interface HelplineDao {
    @Query("SELECT * FROM helpline")
    fun getAllHelpline(): LiveData<List<Helpline>>

    @Query("SELECT * FROM helpline WHERE h_title LIKE :searchQuery")
    fun searchHelplines(searchQuery: String): LiveData<List<Helpline>>

    @Query("SELECT COUNT(h_title) FROM helpline")
    fun getHelplineCount(): LiveData<Int>

    @Query("DELETE FROM helpline")
    suspend fun deleteAllHelpline()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHelpline(helpline: Helpline)

    @Update
    suspend fun updateHelpline(helpline: Helpline)

    @Delete
    suspend fun deleteHelpline(helpline: Helpline)
}