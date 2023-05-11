package com.example.foodhub.user

import android.util.Log
import android.widget.CheckBox
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
}