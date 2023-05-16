package com.example.foodhub.database.daos

import com.example.foodhub.database.tables.UserVolunteeredWork
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserVolunteeredWorkDao {

    @Query("SELECT * FROM volunteered_work WHERE u_id = :id")
    fun getEventsVolunteeredUser(id:String): LiveData<List<UserVolunteeredWork>>
    @Query("SELECT * FROM volunteered_work WHERE v_id = :vid")
    fun getParticipatedUser(vid:Int): LiveData<List<UserVolunteeredWork>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVolunteeredWork(userVolunteeredWork: UserVolunteeredWork)

    @Query("DELETE FROM volunteered_work WHERE v_id = :vid AND u_id = :id")
    suspend fun cancelEventsVolunteeredUser(id:String,vid:Int)

    @Query("UPDATE volunteered_work SET status = :status WHERE uvwId = :uvwId")
    suspend fun updateVolunteeredWorkStatus(uvwId:Int,status:String)

    @Query("UPDATE volunteered_work SET status = :status WHERE v_id = :vId")
    suspend fun updateAllPresent(vId:Int,status:String)
    @Query("SELECT COUNT(u_id) FROM volunteered_work WHERE u_id = :id")
    fun getVolunteeredCount(id:String): LiveData<Int>
    @Query("SELECT COUNT(u_id) FROM volunteered_work WHERE u_id = :id AND v_id = :vid")
    fun checkVolunteered(id:String,vid:Int): LiveData<Int>

    @Query("UPDATE volunteered_work SET u_id = :newID WHERE u_id = :oldID")
    suspend fun updateVWUserID(oldID: String, newID : String)

    @Query("UPDATE volunteered_work SET v_image = :v_image, v_title = :v_title, v_desc = :v_desc, v_street = :v_street, v_city = :v_city, v_postcode = :v_postcode, v_state = :v_state, v_country = :v_country, v_website = :v_website, v_phone = :v_phone, v_reg_link = :v_reg_link, v_maps = :v_maps, v_waze = :v_waze, day = :day,month = :month WHERE v_id = :vId")
    suspend fun updateVolunteeredInfo(vId:Int,v_image:String,v_title:String,v_desc:String,v_street:String,v_city:String,v_postcode:String,v_state:String,v_country:String,v_website:String,v_phone:String,v_reg_link:String,v_maps:String,v_waze:String,day:Int,month:Int)

}