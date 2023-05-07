package com.example.foodhub.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodhub.database.tables.Facility

@Dao
interface FacilityDao {
    @Query("SELECT * FROM facility")
    fun getAllFacility(): LiveData<List<Facility>>

    @Query("SELECT * FROM facility WHERE n_name LIKE :searchQuery")
    fun searchFacilities(searchQuery: String): LiveData<List<Facility>>

    @Query("SELECT COUNT(n_name) FROM facility")
    fun getFacilityCount(): LiveData<Int>

    @Query("DELETE FROM facility")
    suspend fun deleteAllFacility()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFacility(facility: Facility)

    @Update
    suspend fun updateFacility(facility: Facility)

    @Delete
    suspend fun deleteFacility(facility: Facility)
}