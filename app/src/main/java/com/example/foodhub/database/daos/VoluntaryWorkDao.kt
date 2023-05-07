package com.example.foodhub.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodhub.database.tables.VoluntaryWork

@Dao
interface VoluntaryWorkDao {
    @Query("SELECT * FROM voluntaryWork")
    fun getAllWork(): LiveData<List<VoluntaryWork>>

    @Query("SELECT * FROM voluntaryWork WHERE v_title LIKE :searchQuery")
    fun searchWorks(searchQuery: String): LiveData<List<VoluntaryWork>>

    @Query("SELECT COUNT(v_title) FROM voluntaryWork")
    fun getWorkCount(): LiveData<Int>

    @Query("DELETE FROM voluntaryWork")
    suspend fun deleteAllWork()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWork(voluntaryWork: VoluntaryWork)

    @Update
    suspend fun updateWork(voluntaryWork: VoluntaryWork)

    @Delete
    suspend fun deleteWork(voluntaryWork: VoluntaryWork)
}