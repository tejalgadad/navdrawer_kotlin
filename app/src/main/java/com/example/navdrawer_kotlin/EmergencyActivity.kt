package com.example.navdrawer_kotlin

import android.content.Intent
import android.icu.text.IDNA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_emergency.*
import kotlinx.android.synthetic.main.activity_home.*


class EmergencyActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle : ActionBarDrawerToggle
    private var path: String=""
    private lateinit var database : DatabaseReference
    private lateinit var fAuth: FirebaseAuth
    private var key:String =""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)

        path = Firebase.auth.uid.toString()

        val card1 : CardView = findViewById(R.id.card1)
        val card2 : CardView = findViewById(R.id.card2)
        val card3 : CardView = findViewById(R.id.card3)
        val card4 : CardView = findViewById(R.id.card4)



        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val mFragment = Information()

        card1.setOnClickListener {
            Toast.makeText(this,"Emergency Contact 1", Toast.LENGTH_SHORT).show()
                val mBundle = Bundle()
                mBundle.putString("card","card1")
                mFragment.arguments = mBundle
                mFragmentTransaction.add(R.id.frame_container_e, mFragment).commit()
        }
        card2.setOnClickListener {
            Toast.makeText(this,"Emergency Contact 2", Toast.LENGTH_SHORT).show()
            val mBundle = Bundle()
            mBundle.putString("card","card2")
            mFragment.arguments = mBundle
            mFragmentTransaction.add(R.id.frame_container_e, mFragment).commit()

        }
        card3.setOnClickListener {
            Toast.makeText(this,"Emergency Contact 3", Toast.LENGTH_SHORT).show()
            val mBundle = Bundle()
            mBundle.putString("card","card3")
            mFragment.arguments = mBundle
            mFragmentTransaction.add(R.id.frame_container_e, mFragment).commit()
        }
        card4.setOnClickListener {
            Toast.makeText(this,"Emergency Contact 4", Toast.LENGTH_SHORT).show()
            val mBundle = Bundle()
            mBundle.putString("card","card4")
            mFragment.arguments = mBundle
            mFragmentTransaction.add(R.id.frame_container_e, mFragment).commit()
        }
        setData()

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout3)
        val nav_menu_e : NavigationView = findViewById(R.id.nav_menu_emergency)
        toggle  = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled= true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_menu_e.setNavigationItemSelectedListener(this)

    }

    private fun setData() {
        database = FirebaseDatabase.getInstance(
            "https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app"
        ).getReference(path)

        key = path+"1"
        database.child(key).get().addOnSuccessListener {
            if (it.child("name").value.toString() != "null") {
                card_name1.setText((it.child("name").value).toString())
                card_num1.setText((it.child("phone").value).toString())
            }
        }
        key = path +"2"
        database.child(key).get().addOnSuccessListener {
            if (it.child("name").value.toString() != "null") {
                card_name2.setText((it.child("name").value).toString())
                card_num2.setText((it.child("phone").value).toString())
            }
        }

        key = path + "3"
        database.child(key).get().addOnSuccessListener {
            if (it.child("name").value.toString() != "null") {
                card_name3.setText((it.child("name").value).toString())
                card_num3.setText((it.child("phone").value).toString())
            }
        }
            key = path + "4"
            database.child(key).get().addOnSuccessListener {
                if (it.child("name").value.toString() != "null") {
                    card_name4.setText((it.child("name").value).toString())
                    card_num4.setText((it.child("phone").value).toString())
                }
            }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.home ->
            {
                Toast.makeText(applicationContext,"Home", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                //finishAfterTransition()
            }
            R.id.login ->{
                Toast.makeText(applicationContext,"LogOut", Toast.LENGTH_SHORT).show()
                Firebase.auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finishAfterTransition()
            }
            R.id.register ->{
                Toast.makeText(applicationContext,"Register Data", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, EmergencyActivity::class.java)
                startActivity(intent)
                //inishAfterTransition()
            }
            R.id.location ->{
                Toast.makeText(applicationContext,"Location", Toast.LENGTH_SHORT).show()
            }
            R.id.chat ->{
                Toast.makeText(applicationContext,"Chat", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun changeFragment_e(frag: Fragment){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.add(R.id.drawerLayout3,frag).commit()
//        fragment.addToBackStack(frag)
    }
}