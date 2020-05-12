package hu.ait.aitpuritytest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            FragmentWelcome()
        } else {
            FragmentResults()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) "Welcome" else "Results"
    }

    override fun getCount(): Int {
        return 2
    }

}