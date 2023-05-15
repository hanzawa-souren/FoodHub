package com.example.foodhub.database

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.foodhub.admin.viewmodels.VoluntaryWorkViewModel
import com.example.foodhub.database.serializable.SerialVoluntaryWork
import com.example.foodhub.database.tables.VoluntaryWork
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class DBSyncManager {

    companion object {

        val tableArray = listOf<String>("voluntarywork","facility","helpline","latestnews","edigest","user","donation")

        // URL to php file
        const val deleteAllURL: String = "http://10.0.2.2/foodhub/delete_all.php"
        const val updateRowURL: String = "http://10.0.2.2/foodhub/update_row.php"
        const val deleteRowURL: String = "http://10.0.2.2/foodhub/delete_row.php"

        const val fetchAllWorkURL: String = "http://10.0.2.2/foodhub/fetch_all_work.php"
        const val insertWorkURL: String = "http://10.0.2.2/foodhub/insert_work.php"

        fun syncWorkFromRemote(lifecycleOwner: LifecycleOwner, context: Context) {

            val voluntaryWorkDao = AppDatabase.getDatabase(context).voluntaryWorkDao()

            val stringRequest: StringRequest =
                object : StringRequest(
                    Request.Method.POST, fetchAllWorkURL,
                    Response.Listener { response ->

                        val json = Json { encodeDefaults = true }
                        val workList: List<SerialVoluntaryWork> = json.decodeFromString(response)

                        //val localImageFileNames: MutableList<String> = mutableListOf()

                        for (i in workList.indices) {
                            //val workImage = TypeConverter.base64ToBitmap(workList[i].vImage)
                            val workId = workList[i].vId
                            val workImage = workList[i].vImage
                            Log.d("workList[i].vImage", workList[i].vImage)
                            val workTitle = workList[i].vTitle
                            val workDesc = workList[i].vDesc
                            val workStreet = workList[i].vStreet
                            val workCity = workList[i].vCity
                            val workPostcode = workList[i].vPostcode
                            val workState = workList[i].vState
                            val workCountry = workList[i].vCountry
                            val workWebsite = workList[i].vWebsite
                            val workPhone = workList[i].vPhone
                            val workReglink = workList[i].vRegLink
                            val workMaps = workList[i].vMaps
                            val workWaze = workList[i].vWaze

                            val currentTime = Calendar.getInstance().time
                            val formatter = SimpleDateFormat("yyyyMMdd_HH_mm_ss")
                            val timestamp = formatter.format(currentTime).toString()
                            val imageFileName = "v_work_img_${timestamp}_${(10000..100000).random()}"

                            val workImagePath = ImageStorageManager.saveToInternalStorage(context, TypeConverter.base64ToBitmap(workImage), imageFileName)

                            val workEntity = VoluntaryWork(
                                workId, workImagePath, workTitle, workDesc, workStreet, workCity, workPostcode, workState, workCountry, workWebsite, workPhone, workReglink, workMaps, workWaze
                            )

                            CoroutineScope(Dispatchers.IO).launch {
                                voluntaryWorkDao.insertWork(workEntity)
                            }
                        }
                    },
                    Response.ErrorListener { error ->
                        Log.d("DBSyncManager", error.toString())
                    }) {
                    /*@Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val data: MutableMap<String, String> = HashMap()
                        data["tablename"] = tableArray[0]
                        return data
                    }*/
                }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun insertWork(lifecycleOwner: LifecycleOwner, context: Context, newWork: VoluntaryWork) {

            CoroutineScope(Dispatchers.IO).launch {

                /*val imageFileName = newWork.vImage.substring(newWork.vImage.lastIndexOf("/")+1)
                val imageBitmap = ImageStorageManager.getImageFromInternalStorage(context, imageFileName)
                var vImageBase64: String = ""
                if (imageBitmap != null) {
                    vImageBase64 = TypeConverter.bitmapToBase64(imageBitmap)
                }*/

                val stringRequest: StringRequest = object : StringRequest(
                    Request.Method.POST, insertWorkURL,
                    Response.Listener { response ->
                        if (response == "success") {
                            syncWorkFromRemote(lifecycleOwner, context)
                        }
                    },
                    Response.ErrorListener { error ->
                        Log.d("DBSyncManager", error.toString())
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val data: MutableMap<String, String> = HashMap()
                        data["vImage"] = newWork.vImage
                        data["vTitle"] = newWork.vTitle
                        data["vDesc"] = newWork.vDesc
                        data["vStreet"] = newWork.vStreet
                        data["vCity"] = newWork.vCity
                        data["vPostcode"] = newWork.vPostcode
                        data["vState"] = newWork.vState
                        data["vCountry"] = newWork.vCountry
                        data["vPhone"] = newWork.vPhone
                        data["vWebsite"] = newWork.vWebsite
                        data["vRegLink"] = newWork.vRegLink
                        data["vMaps"] = newWork.vRegLink
                        data["vWaze"] = newWork.vWaze
                        return data
                    }
                }
                val requestQueue = Volley.newRequestQueue(context)
                requestQueue.add(stringRequest)

            }
        }

        fun deleteWork(lifecycleOwner: LifecycleOwner, context: Context, oldWork: VoluntaryWork) {

            CoroutineScope(Dispatchers.IO).launch {

                val stringRequest: StringRequest = object : StringRequest(
                    Request.Method.POST, deleteRowURL,
                    Response.Listener { response ->
                        if (response == "success") {
                            syncWorkFromRemote(lifecycleOwner, context)
                        }
                    },
                    Response.ErrorListener { error ->
                        Log.d("DBSyncManager", error.toString())
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val data: MutableMap<String, String> = HashMap()
                        data["rowid"] = oldWork.vId.toString()
                        return data
                    }
                }
                val requestQueue = Volley.newRequestQueue(context)
                requestQueue.add(stringRequest)
            }
        }

    }

}