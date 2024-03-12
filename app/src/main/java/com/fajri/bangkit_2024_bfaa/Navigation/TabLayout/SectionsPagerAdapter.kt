package com.fajri.bangkit_2024_bfaa.Navigation.TabLayout

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fajri.bangkit_2024_bfaa.Navigation.TabLayout.home.HomeFragment
import com.fajri.bangkit_2024_bfaa.Navigation.TabLayout.profile.ProfileFragment

class SectionsPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HomeFragment()
            1 -> fragment = ProfileFragment()
        }
        return fragment as Fragment
    }
}