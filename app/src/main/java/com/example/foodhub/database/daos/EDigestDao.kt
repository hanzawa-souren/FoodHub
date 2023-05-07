package com.example.foodhub.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodhub.database.tables.EDigest

@Dao
interface EDigestDao {
    @Query("SELECT * FROM eDigest")
    fun getAllDigest(): LiveData<List<EDigest>>

    @Query("SELECT * FROM eDigest WHERE e_title LIKE :searchQuery")
    fun searchDigests(searchQuery: String): LiveData<List<EDigest>>

    @Query("SELECT COUNT(e_title) FROM eDigest")
    fun getDigestCount(): LiveData<Int>

    @Query("DELETE FROM eDigest")
    suspend fun deleteAllDigest()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDigest(eDigest: EDigest)

    @Update
    suspend fun updateDigest(eDigest: EDigest)

    @Delete
    suspend fun deleteDigest(eDigest: EDigest)
}