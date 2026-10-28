package com.example.basekotlin.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.basekotlin.ui.main.channel.ChannelFragment
import com.example.basekotlin.ui.main.home.HomeFragment

class MainPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = PAGE_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            HOME_PAGE -> HomeFragment()
            CHANNEL_PAGE -> ChannelFragment()
            else -> error("Unsupported page position: $position")
        }
    }

    companion object {
        const val HOME_PAGE = 0
        const val CHANNEL_PAGE = 1
        private const val PAGE_COUNT = 2
    }
}
