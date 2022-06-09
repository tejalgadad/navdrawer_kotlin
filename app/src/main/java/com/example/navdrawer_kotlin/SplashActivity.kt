package com.example.navdrawer_kotlin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
//import android.support.v4.app.Fragment
//import android.support.v4.app.FragmentPagerAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.cuberto.liquid_swipe.LiquidPager

class SplashActivity : AppCompatActivity() {

//    private var NUM_PAGES = 3
//    private lateinit var viewPager: ViewPager
//     private lateinit var pageAdapter: ScreenSlidePageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        val backgroundImg: ImageView = findViewById(R.id.SplashScreenImage)
        val fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade)

        //backgroundImg2.startAnimation(sideAnimation)
        backgroundImg.startAnimation(fadeAnimation)
        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },3000)

    }
}

