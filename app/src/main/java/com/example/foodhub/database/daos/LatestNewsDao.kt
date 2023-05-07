package com.example.foodhub.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodhub.database.tables.LatestNews

@Dao
interface LatestNewsDao {
    @Query("SELECT * FROM latestNews")
    fun getAllNews(): LiveData<List<LatestNews>>

    @Query("SELECT * FROM latestNews WHERE ln_title LIKE :searchQuery")
    fun searchNews(searchQuery: String): LiveData<List<LatestNews>>

    @Query("SELECT COUNT(ln_title) FROM latestNews")
    fun getNewsCount(): LiveData<Int>

    @Query("DELETE FROM latestNews")
    suspend fun deleteAllNews()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(latestNews: LatestNews)

    @Update
    suspend fun updateNews(latestNews: LatestNews)

    @Delete
    suspend fun deleteNews(latestNews: LatestNews)
}