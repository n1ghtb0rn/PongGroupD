package com.kyhgroupd.ponggroupd.activitys

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.kyhgroupd.ponggroupd.TabPageAdapter
import com.kyhgroupd.ponggroupd.databinding.ActivityHighScoreBinding

class HighScoreActivity : AppCompatActivity() {

    private lateinit var binder : ActivityHighScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityHighScoreBinding.inflate(layoutInflater)
        setContentView(binder.root)
        setUpTabBar()

        binder.btnBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpTabBar() {
        val adapter = TabPageAdapter(this, binder.tabLayout.tabCount)
        binder.viewPager.adapter = adapter

        binder.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback()
        {
            override fun onPageSelected(position: Int) {
                binder.tabLayout.selectTab(binder.tabLayout.getTabAt(position))
            }
        })

        binder.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener
        {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binder.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}