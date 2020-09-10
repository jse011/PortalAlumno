package com.consultoraestrategia.ss_portalalumno.exoPlayer.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.consultoraestrategia.ss_portalalumno.R

private val TAB_TITLES = arrayOf(
    "tab_circle",
    "tab_seconds_view"
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val fragmentList: ArrayList<Fragment> = arrayListOf()

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getPageTitle(position: Int): CharSequence? =
       TAB_TITLES[position]

    override fun getCount(): Int = fragmentList.size
}