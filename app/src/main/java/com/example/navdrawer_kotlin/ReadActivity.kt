package com.example.navdrawer_kotlin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navdrawer_kotlin.model.BlogPost
import com.example.navdrawer_kotlin.model.BlogRecyclerAdapter
import kotlinx.android.synthetic.main.activity_read.*

class ReadActivity : AppCompatActivity() {
    val openURL = Intent(Intent.ACTION_VIEW)
    private lateinit var blogAdapter : BlogRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        findViewById<Button>(R.id.back).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finishAfterTransition()
        }
        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet(){
        val data = DataSource.createDataSet()
        blogAdapter.submitList(data)
    }

    private fun initRecyclerView(){

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ReadActivity)
            val topSpacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            blogAdapter = BlogRecyclerAdapter(){ position: Int ->
                Log.e("HomeActivity", "Clicked on item at position $position")
                Toast.makeText(this@ReadActivity, "Blog $position", Toast.LENGTH_SHORT).show()
                if(position==0){
                    openURL.data = Uri.parse("https://www.cosmopolitan.com/lifestyle/advice/a4364/safety-tips-every-woman-should-know/")
                    startActivity(openURL)
                }
                else if(position==1){
                    openURL.data = Uri.parse("https://www.indiatoday.in/lifestyle/people/story/women-safety-eve-teasing-men-harassment-lifest-973449-2017-04-25")
                    startActivity(openURL)
                }
                else if(position==2){
                    openURL.data = Uri.parse("https://www.freepressjournal.in/cmcm/international-womens-day-2018-5-safety-tips-for-women")
                    startActivity(openURL)
                }
                else if(position==3){
                    openURL.data = Uri.parse("https://www.bbc.com/news/technology-56373292")
                    startActivity(openURL)
                }
                else if(position==4){
                    openURL.data = Uri.parse("https://issuesiface.com/magazine/top-10-safety-tips-for-women")
                    startActivity(openURL)
                }
                else if(position==5){
                    openURL.data = Uri.parse("https://indianhelpline.com/WOMEN-HELPLINE/")
                    startActivity(openURL)
                }
                else if(position==6){
                    openURL.data = Uri.parse("https://www.healthline.com/health/womens-health/self-defense-tips-escape")
                    startActivity(openURL)
                }
                else if(position==7){
                    openURL.data = Uri.parse("https://vikaspedia.in/health/first-aid/basic-tips")
                    startActivity(openURL)
                }
                else if(position==8){
                    openURL.data = Uri.parse("https://www.villageofshorewood.org/281/Preventative-Measures-Against-Rape")
                    startActivity(openURL)
                }
            }
            adapter= blogAdapter

        }
    }
}