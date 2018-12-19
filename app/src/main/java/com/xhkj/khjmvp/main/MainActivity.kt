package com.xhkj.khjmvp.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.litesuits.common.assist.Toastor
import com.litesuits.common.utils.VibrateUtil
import com.xhkj.khjmvp.R
import com.xhkj.khjmvp.mvp.file.FileFragment
import com.xhkj.khjmvp.mvp.file.FileFragment1
import com.xhkj.khjmvp.mvp.home.HomeFragment
import com.xhkj.khjmvp.mvp.monitort.MonitorFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

   /* override fun getContextViewId(): Int {
        return R.layout.activity_main
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) initPage()
    }

    private var mExitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toastor(this).showToast("再按一次退出")
                mExitTime = System.currentTimeMillis()

            } else {
                finish()
            }
            return true
        }
        return false
    }

    private fun initPage() {

        val mTitles: Array<String> = arrayOf("首页", "监控", "文件")
        val mIconUnSelectIds: IntArray = intArrayOf(R.drawable.ic_home, R.drawable.ic_monitor, R.drawable.ic_file)
        val mIconSelectIds: IntArray = intArrayOf(R.drawable.ic_home_sel, R.drawable.ic_monitor_sel, R.drawable.ic_file_sel)

        val mTabEntities = ArrayList<CustomTabEntity>()
        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]))
        }

        tabLayout.setTabData(mTabEntities)

        val items: ArrayList<Pair<String, Fragment>> = ArrayList()
        items.add(Pair("首页", HomeFragment()))
        items.add(Pair("监控", MonitorFragment()))
        items.add(Pair("文件", FileFragment1()))

        val pageAdapter = HomePageAdapter(supportFragmentManager, items)
        viewPager.adapter = pageAdapter
        //tabLayout.setViewPager(viewPager)
        viewPager.offscreenPageLimit = pageAdapter.count
        viewPager.currentItem = 0

        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {

            override fun onTabSelect(position: Int) {
                viewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {
                VibrateUtil.vibrate(this@MainActivity, 100)
                Toastor(this@MainActivity).showToast("这里还可以加功能")
            }
        })

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                tabLayout.currentTab = position
            }
        })
    }

}
