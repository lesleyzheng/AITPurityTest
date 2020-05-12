package hu.ait.aitpuritytest

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerTitleStrip
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*


class MainActivity : AppCompatActivity() {

    private lateinit var pagerTitleStrip: PagerTitleStrip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        viewPager.adapter = MyPagerAdapter(supportFragmentManager)

        pagerTitleStrip = findViewById(R.id.viewPagerTab)
        pagerTitleStrip.setBackgroundColor(Color.rgb(153,153,204))

    }

}