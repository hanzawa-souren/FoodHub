package com.example.foodhub.database.repositories

import com.example.foodhub.database.tables.UserVolunteeredWork
import androidx.lifecycle.LiveData
import com.example.foodhub.database.daos.UserVolunteeredWorkDao

class UserVolunteeredWorkRepo(private val userVolunteeredWorkDao: UserVolunteeredWorkDao) {

    fun getEventsVolunteeredUser(id:String): LiveData<List<UserVolunteeredWork>> {
        return userVolunteeredWorkDao.getEventsVolunteeredUser(id)
    }
    suspend fun addVolunteeredWork(userVolunteeredWork: UserVolunteeredWork){
        userVolunteeredWorkDao.addVolunteeredWork(userVolunteeredWork)
    }
    suspend fun cancelEventsVolunteeredUser(id:String){
        userVolunteeredWorkDao.cancelEventsVolunteeredUser(id)
    }
    suspend fun updateVolunteeredWorkStatus(uvwId:String,status:String){
        userVolunteeredWorkDao.updateVolunteeredWorkStatus(uvwId,status)
    }
    fun checkVolunteered(id:String,vid:Int): LiveData<Int>{
        return userVolunteeredWorkDao.checkVolunteered(id,vid)
    }
    suspend fun updateVolunteeredInfo(vId:Int,v_image:String,v_title:String,v_desc:String,v_street:String,v_city:String,v_postcode:String,v_state:String,v_country:String,v_website:String,v_phone:String,v_reg_link:String,v_maps:String,v_waze:String,day:Int,month:Int){
        userVolunteeredWorkDao.updateVolunteeredInfo(vId,v_image,v_title,v_desc,v_street,v_city,v_postcode,v_state,v_country,v_website,v_phone,v_reg_link,v_maps,v_waze,day,month)
    }
    suspend fun updateVWUserID(oldID: String, newID : String){
        userVolunteeredWorkDao.updateVWUserID(oldID, newID)
    }
}