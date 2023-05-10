package com.example.foodhub.user.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodhub.user.models.HelpLineModel

class HelpLineViewModel:ViewModel() {
    var HelpLineSelected = MutableLiveData<HelpLineModel>()
}