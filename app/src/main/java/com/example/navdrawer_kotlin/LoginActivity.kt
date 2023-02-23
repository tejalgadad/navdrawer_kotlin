package com.example.navdrawer_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cuberto.liquid_swipe.LiquidPager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        supportFragmentManager.beginTransaction()
//            .add(R.id.drawerLayout2,Login()).addToBackStack(null)
//            .commit()

        val nextButton : FloatingActionButton = findViewById(R.id.next)
        nextButton.setOnClickListener {
            val intent = Intent(this, SignInUp::class.java)
            startActivity(intent)
        }
        val viewPager = findViewById<LiquidPager>(R.id.pager)
        viewPager.adapter = ScreenSlidePageAdapter(supportFragmentManager)
        val fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade)
        viewPager.startAnimation(fadeAnimation)
    }


    class ScreenSlidePageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        private val total:Int = 3
        private val data : ArrayList<OnBoardFragment> = ArrayList(total)

        init {
            for (i in 0 until total) {
                val fragment = OnBoardFragment()
                val bundle = Bundle()
                bundle.putInt("POSITION", i+1)
                fragment.arguments = bundle
                data.add(fragment)
            }
        }


        override fun getItem(position: Int): Fragment {
            return data[position]
        }

        override fun getCount(): Int {
            return total
        }

    }
}