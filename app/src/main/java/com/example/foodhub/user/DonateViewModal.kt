package com.example.foodhub.user

import android.graphics.Bitmap
import android.util.Log
import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodhub.database.tables.Donation
import com.example.foodhub.login.User
import kotlin.math.log

class DonateViewModal:ViewModel() {
    var donateMethod = MutableLiveData<String>()
    var donateAmount = MutableLiveData<String>()
    var tnc = MutableLiveData<Boolean>(false) // initialize with false
    var userID = MutableLiveData<String>()
    var donateAmounts =MutableLiveData<Double>()
    var day =MutableLiveData<Int>()
    var month =MutableLiveData<Int>()
    var year =MutableLiveData<Int>()

    var donateHistoryAmount = MutableLiveData<String>()
    var donateHistoryPaymentMethod = MutableLiveData<String>()
    var donateHistoryDate = MutableLiveData<String>()
    var donateHistoryRef = MutableLiveData<String>()
    var donateHistoryCardNumber = MutableLiveData<String>()

    var name = MutableLiveData<String>()
    var numEventsVolunteered= MutableLiveData<Int>()

    var evAboutUs = MutableLiveData<String>()
    var evLocation = MutableLiveData<String>()
    var evWebPage = MutableLiveData<String>()
    var evPhone = MutableLiveData<String>()
    var evDate = MutableLiveData<String>()
    var evStatus = MutableLiveData<String>()
    var evWaze= MutableLiveData<String>()
    var evGmap= MutableLiveData<String>()
    var evImage = MutableLiveData<String>()
    var evVid = MutableLiveData<Int>()
    var evTitle= MutableLiveData<String>()

    var adminVid= MutableLiveData<Int>()
    var checkId= MutableLiveData<String>()
    var checkUvid = MutableLiveData<Int>()

    var idCount =MutableLiveData<Int>(0)

    fun updateTncValue(value: Boolean) {
        Log.d("Cbox", "True");
        tnc.value = value
    }
    fun checkTnc(value: Boolean){
        var value = tnc.value?:false
        if(!value){
            Log.d("Cbox", "false");
        }else {
            Log.d("Cbox", "true");
        }

    }
    fun setString(text:String){
        userID.value = text
    }
//    fun setImage(bitmap: Bitmap) {
//        evImage.value = bitmap
//    }
//
//    fun getImage(): LiveData<Bitmap> {
//        return evImage
//    }
}