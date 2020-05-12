package hu.ait.aitpuritytest

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerTitleStrip
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*


class MainActivity : AppCompatActivity() {

    private lateinit var pagerTitleStrip: PagerTitleStrip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false);

        viewPager.adapter = MyPagerAdapter(supportFragmentManager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                (viewPager.adapter as MyPagerAdapter).notifyDataSetChanged()
            }

        })

        pagerTitleStrip = findViewById(R.id.viewPagerTab)
        pagerTitleStrip.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        pagerTitleStrip.setTextColor(resources.getColor(R.color.colorSecondaryVariant))


    }

}