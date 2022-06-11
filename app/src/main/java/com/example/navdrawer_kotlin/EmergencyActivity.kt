package com.example.navdrawer_kotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_emergency.*


class EmergencyActivity : AppCompatActivity(){ //, NavigationView.OnNavigationItemSelectedListener {

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

    fun changeFragment_e(frag: Fragment){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.add(R.id.drawerLayout3,frag).commit()
//        fragment.addToBackStack(frag)
    }
}