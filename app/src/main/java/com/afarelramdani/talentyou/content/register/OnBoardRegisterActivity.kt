package com.afarelramdani.talentyou.content.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityOnBoarRegisterBinding

class OnBoardRegisterActivity : BaseActivity<ActivityOnBoarRegisterBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_on_boar_register
        super.onCreate(savedInstanceState)


        binding.btnRegisterRecruiter.setOnClickListener(this)
        binding.btnRegisterTalent.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_register_talent -> {
                baseStartActivity<RegisterTalentActivity>(this)
            }
            R.id.btn_register_recruiter -> {
                baseStartActivity<RegisterRecruiterActivity>(this)
            }
        }
    }
}