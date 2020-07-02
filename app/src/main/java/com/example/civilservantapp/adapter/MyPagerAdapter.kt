package com.example.civilservantapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.civilservantapp.view.fragment.OtherFragment
import com.example.civilservantapp.view.fragment.PersonFragment
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit


class MyPagerAdapter(fragmentManager: FragmentManager, var personHeader:String, var otherHeader:String) :
    FragmentStatePagerAdapter(fragmentManager) {

    // Returns total number of pages
    override fun getCount(): Int {
        return NUM_ITEMS
    }

    // Returns the fragment to display for that page
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 // Fragment # 0 - This will show FirstFragment
            -> return PersonFragment()
            1 // Fragment # 0 - This will show FirstFragment different title
            -> return OtherFragment()
            else -> return Fragment()
        }
    }

    // Returns the page title for the top indicator
    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0-> return personHeader
            1-> return otherHeader
            else->return ""
        }
    }

    companion object {
        private val NUM_ITEMS = 2
    }

}