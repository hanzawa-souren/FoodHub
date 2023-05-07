package com.example.foodhub.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodhub.database.daos.*
import com.example.foodhub.database.tables.*
import com.example.foodhub.login.User
import com.example.foodhub.login.UserDAO

@Database(entities = [User::class, VoluntaryWork::class, Facility::class, Helpline::class, LatestNews::class, EDigest::class, Donation::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO
    abstract fun voluntaryWorkDao(): VoluntaryWorkDao
    abstract fun facilityDao(): FacilityDao
    abstract fun helplineDao(): HelplineDao
    abstract fun latestNewsDao(): LatestNewsDao
    abstract fun eDigestDao(): EDigestDao
    abstract fun donationDao(): DonationDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}