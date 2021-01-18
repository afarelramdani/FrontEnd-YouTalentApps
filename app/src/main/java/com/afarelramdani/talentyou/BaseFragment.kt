package com.afarelramdani.talentyou

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.afarelramdani.talentyou.util.SharedPreferences

abstract class BaseFragment<FragmentBase: ViewDataBinding> : Fragment() {
    protected lateinit var binding: FragmentBase
    protected var setLayout: Int? = null
    protected lateinit var sharePref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, setLayout!!, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharePref = SharedPreferences(view.context)
    }

    protected inline fun <reified Activity> baseStartActivity(context: Context) {
        context.startActivity(Intent(context, Activity::class.java))
    }

}



