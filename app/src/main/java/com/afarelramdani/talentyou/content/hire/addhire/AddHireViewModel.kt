//package com.afarelramdani.talentyou.content.hire.addhire
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.afarelramdani.talentyou.content.experience.ExperienceModel
//import com.afarelramdani.talentyou.util.ApiService
//import com.afarelramdani.talentyou.util.SharedPreferences
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlin.coroutines.CoroutineContext
//
//class AddHireViewModel: ViewModel(), CoroutineScope {
//    val isHireLiveData = MutableLiveData<Boolean>()
//    var listProjectSpinner = MutableLiveData<List<ExperienceModel>>()
//
//    private lateinit var service: ApiService
//    private lateinit var sharedPref: SharedPreferences
//
//    override val coroutineContext: CoroutineContext
//        get() = Job() + Dispatchers.Main
//
//    fun setSharedPreference(sharedPreferences: SharedPreferences) {
//        this.sharedPref = sharedPreferences
//    }
//
//    fun setLoginService(service: ApiService) {
//        this.service = service
//    }
//}