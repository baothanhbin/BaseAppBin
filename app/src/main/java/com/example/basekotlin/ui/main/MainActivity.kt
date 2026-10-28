package com.example.basekotlin.ui.main

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityMainBinding
import com.example.basekotlin.dialog.exit.ExitAppDialog

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateBottomBar(isHomeSelected = position == MainPagerAdapter.HOME_PAGE)
        }
    }

    override fun initView() {
        binding.mainViewPager.apply {
            adapter = MainPagerAdapter(this@MainActivity)
            offscreenPageLimit = 1
            registerOnPageChangeCallback(pageChangeCallback)
        }
        updateBottomBar(
            isHomeSelected = binding.mainViewPager.currentItem == MainPagerAdapter.HOME_PAGE,
        )
    }

    override fun bindView() {
        binding.bottomNav.tabHome.tap {
            binding.mainViewPager.setCurrentItem(MainPagerAdapter.HOME_PAGE, true)
        }
        binding.bottomNav.tabChannel.tap {
            binding.mainViewPager.setCurrentItem(MainPagerAdapter.CHANNEL_PAGE, true)
        }
        binding.bottomNav.tabAdd.setOnClickListener(null)
    }

    override fun onBack() {
        ExitAppDialog(this, true) {
            finishAffinity()
        }.show()
    }

    private fun updateBottomBar(isHomeSelected: Boolean) {
        val selectedColor = ContextCompat.getColor(this, R.color.white)
        val unselectedColor = ContextCompat.getColor(this, R.color.chip_channel)
        val homeColor = if (isHomeSelected) selectedColor else unselectedColor
        val channelColor = if (isHomeSelected) unselectedColor else selectedColor

        binding.bottomNav.bottomBar.setBackgroundResource(
            if (isHomeSelected) R.drawable.bg_bottom_bar_home
            else R.drawable.bg_bottom_bar_channel,
        )
        binding.bottomNav.tabHome.setBackgroundResource(
            if (isHomeSelected) R.drawable.bg_green_corner else R.drawable.bg_channel,
        )
        binding.bottomNav.tabChannel.setBackgroundResource(
            if (isHomeSelected) R.drawable.bg_channel else R.drawable.bg_green_corner,
        )
        binding.bottomNav.tvHomeTab.setTextColor(homeColor)
        binding.bottomNav.tvChannelTab.setTextColor(channelColor)
        ImageViewCompat.setImageTintList(
            binding.bottomNav.ivHomeTab,
            ColorStateList.valueOf(homeColor),
        )
        ImageViewCompat.setImageTintList(
            binding.bottomNav.ivChannelTab,
            ColorStateList.valueOf(channelColor),
        )
    }

    override fun onDestroy() {
        binding.mainViewPager.unregisterOnPageChangeCallback(pageChangeCallback)
        binding.mainViewPager.adapter = null
        super.onDestroy()
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }
}
