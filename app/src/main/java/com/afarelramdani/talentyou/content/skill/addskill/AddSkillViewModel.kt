package com.afarelramdani.talentyou.content.skill.addskill

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddSkillViewModel: ViewModel(), CoroutineScope {
    val isAddSkillLiveData = MutableLiveData<Boolean>()
    private lateinit var service: ApiService
    private lateinit var sharedPref: SharedPreferences

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSharedPreference(sharedPreferences: SharedPreferences) {
        this.sharedPref = sharedPreferences
    }

    fun setAddSkillService(service: ApiService) {
        this.service = service
    }


    fun AddSkill(enId: Int, skillName: String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.addSkill(enId, skillName)
                } catch (e:Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isAddSkillLiveData.value = false
                    }
                }
            }
            if (result is AddSkillResponse) {
                if (result.success) {
                    isAddSkillLiveData.value = true
                } else{
                    isAddSkillLiveData.value = false
                }
            }
        }
    }
}