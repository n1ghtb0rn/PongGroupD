package com.kyhgroupd.ponggroupd

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kyhgroupd.ponggroupd.highscorefragments.BreakoutFragment
import com.kyhgroupd.ponggroupd.highscorefragments.GolfFragment
import com.kyhgroupd.ponggroupd.highscorefragments.PongFragment

class TabPageAdapter(activity: FragmentActivity, private val tabCount: Int) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return when (position)
        {
            0 -> BreakoutFragment()
            1 -> PongFragment()
            2 -> GolfFragment()
            else -> BreakoutFragment()
        }
    }
}