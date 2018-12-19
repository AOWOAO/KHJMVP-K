package com.xhkj.khjmvp.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList

class HomePageAdapter(fm: FragmentManager, private val items: ArrayList<Pair<String, Fragment>>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return items[position].second
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return items[position].first
    }

}
