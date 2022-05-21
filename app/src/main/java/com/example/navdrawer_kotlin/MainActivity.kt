package com.example.navdrawer_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), fragmentNavigation{  //LocationListener
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fAuth= Firebase.auth
        val currentUser = fAuth.currentUser
        if(currentUser!=null){
//            supportFragmentManager.beginTransaction()
//                .add(R.id.drawerLayout,Blank()).addToBackStack(null)
////                .commit()
//            changeFragment(Home())
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finishAfterTransition()
        }
        else{
//            supportFragmentManager.beginTransaction()
//                .add(R.id.drawerLayout,Home()).addToBackStack(null)
//                .commit()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAfterTransition()
        }

    }

    override fun navigateFrag(fragment: Fragment, addToStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.drawerLayout,fragment)

        if(addToStack){
            transaction.addToBackStack(null)
        }
        else {
            supportFragmentManager.popBackStack(null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE)

        }
        supportFragmentManager.executePendingTransactions()
        transaction.commit()
    }

}