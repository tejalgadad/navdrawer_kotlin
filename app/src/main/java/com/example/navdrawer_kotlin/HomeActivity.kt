package com.example.navdrawer_kotlin

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.content_main.*
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity(),LocationListener, NavigationView.OnNavigationItemSelectedListener, fragmentNavigation {
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var fAuth: FirebaseAuth
    lateinit var locationManager: LocationManager
    val locationPermissionCode = 2
    val permissionRequest = 101
    val myNumber: Long = 8237827284
    var myMsg: String = "hi"
    var flag: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val nav_menu : NavigationView = findViewById(R.id.nav_menu)
        toggle  = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled= true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_menu.setNavigationItemSelectedListener(this)

       findViewById<Button>(R.id.btn_log_out).setOnClickListener {
           Firebase.auth.signOut()
           val intent = Intent(this, LoginActivity::class.java)
           startActivity(intent)
           finishAfterTransition()
            }

        findViewById<Button>(R.id.btn_help).setOnClickListener {
            getLocation()
            Toast.makeText(this, "Activated", Toast.LENGTH_SHORT).show()
            flag = 1
        }

        findViewById<Button>(R.id.btn_stop).setOnClickListener {
            Toast.makeText(this, "Deactivated", Toast.LENGTH_SHORT).show()
            flag=0
        }

        }

    private fun myMessage() {
        if (myMsg != "hi") {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(myNumber.toString(), null, myMsg, null, null)
            Toast.makeText(this,"Message Sent", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLocationChanged(location: Location) {
        myMsg = "http://maps.google.com/?q=" + location.latitude + "," + location.longitude

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            if(flag==1){
                myMessage()
            }
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.SEND_SMS),
                permissionRequest
            )
        }
    }

    private fun getLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, this)
    }


    //LOCATION
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == permissionRequest) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(
                    this, "You don't have required permission to send a message",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.home ->
            {
                Toast.makeText(applicationContext,"Home",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finishAfterTransition()
            }
            R.id.login ->{
                Toast.makeText(applicationContext,"Login",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finishAfterTransition()
            }
            R.id.register ->{
                Toast.makeText(applicationContext,"Register Data",Toast.LENGTH_SHORT).show()
                changeFragment(Register())
                // changeFragment(Register())
            }
            R.id.location ->{
                Toast.makeText(applicationContext,"Location",Toast.LENGTH_SHORT).show()
                changeFragment(Register())
            }
            R.id.chat ->{
                Toast.makeText(applicationContext,"Chat",Toast.LENGTH_SHORT).show()
                changeFragment(Blank())
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

    fun changeFragment(frag: Fragment){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.frame_container,frag).commit()
//        fragment.addToBackStack(frag)
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
