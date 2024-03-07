package com.fajri.bangkit_2024_bfaa.Fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fajri.bangkit_2024_bfaa.R

class LatihanFragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_fragment)

        val fragmentManager = supportFragmentManager
        val homeFragment = HomeFragment()
        val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)
        if (fragment !is HomeFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + HomeFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }
    }
}