package com.example.navdrawer_kotlin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity(),LocationListener,  fragmentNavigation {
    private var path: String = ""
    private var key: String = ""
    private lateinit var fAuth: FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var databaseS : DatabaseReference
    private lateinit var databaseD : DatabaseReference
    lateinit var locationManager: LocationManager
    val locationPermissionCode = 2
    val permissionRequest = 101
    var myMsg: String = ""
    var msg:String=""
    var flag: Int = 0
    var dangerFlag: Int = 0
    private var serialNo:Long =1
    var prevNo:Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        path = Firebase.auth.uid.toString()
        val name= Firebase.auth.currentUser?.email

        findViewById<TextView>(R.id.username).text = name.toString()

        findViewById<Button>(R.id.btn_help).setOnClickListener {
            getLocation()
            Toast.makeText(this, "Activated", Toast.LENGTH_SHORT).show()
            serialNumber()
            flag = 1
            dangerFlag = 1
        }

        findViewById<Button>(R.id.btn_stop).setOnClickListener {
            Toast.makeText(this, "Deactivated", Toast.LENGTH_SHORT).show()
            flag = 0
        }

        val recyclerview = findViewById<RecyclerView>(R.id.rv_1)
        recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val data = ArrayList<StaticRvModel>()

        data.add(StaticRvModel(R.drawable.homelogo, "Home"))
        data.add(StaticRvModel(R.drawable.location, "Location"))
        data.add(StaticRvModel(R.drawable.reg, "Register"))
        data.add(StaticRvModel(R.drawable.readbooks, "Read"))
        data.add(StaticRvModel(R.drawable.logout2, "SignOut"))
      //  val recycleradapter = StaticRvAdapter(data)
//        recyclerview.adapter = adapter
        recyclerview?.adapter = StaticRvAdapter(data) { itemDto: StaticRvModel, position: Int ->
            Log.e("HomeActivity", "Clicked on item  ${itemDto.text} at position $position")
            if(position==0){
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
            }
            else if(position ==1){
                Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LocationActivity::class.java)
                startActivity(intent)
            }
            else if(position==2){
                Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, EmergencyActivity::class.java)
                startActivity(intent)
            }
            else if(position==3){
                Toast.makeText(this, "Read Blogs", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, LocationActivity::class.java)
//                startActivity(intent)
            }
            else if(position==4){
                Toast.makeText(this, "SignOut", Toast.LENGTH_SHORT).show()
                Firebase.auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finishAfterTransition()
            }
        }

    }


    private fun serialNumber(){
        databaseS = FirebaseDatabase.getInstance("https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app"
        ).getReference("Serial")
        databaseS.child("number").get().addOnSuccessListener{
            if(it.value==null){
                serialNo = 1
                databaseS.child("number").setValue(serialNo).addOnSuccessListener {
                }
                Log.e("null", "null")
            }
            else if(it.value !=null){
                Log.e("no null", "no null")
                prevNo = it.value as Long
                serialNo=prevNo +1
                databaseS.child("number").setValue(serialNo).addOnSuccessListener {
                }
            }
            Log.e("prev", prevNo.toString())
            Log.e("current", serialNo.toString())
        }

    }

    private fun readData(i: Int){
        var message =""
        var phone =""
        database = FirebaseDatabase.getInstance("https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app"
        ).getReference(path)
        key=path+ i.toString()
        Log.i("in loop ",i.toString())
        Log.i("key",key)

       database.child(key).get().addOnSuccessListener {
           if(it.child("name").value.toString() != "null") {
               Log.i("database", "accessed")
               phone = it.child("phone").value.toString()
               message = it.child("message").value.toString()
               msg = message+myMsg
               val smsManager: SmsManager = SmsManager.getDefault()
               smsManager.sendTextMessage(phone, null, msg, null, null)
               Log.i("msg", "sendingmsg")
               Log.i("flag", flag.toString())
               Log.i("phone", phone)
           }

       }.addOnFailureListener{
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }
    }



    private fun myMessage() {
        Log.i(",message","my message")

        for (i in 1..4){
            readData(i)
        }
        Toast.makeText(this,"Message Sent", Toast.LENGTH_SHORT).show()
    }


    override fun onLocationChanged(location: Location) {
        myMsg = " http://maps.google.com/?q=" + location.latitude + "," + location.longitude

        val loc = LocationLogging(location.latitude,location.longitude )
        if(dangerFlag==1) {
            databaseD = FirebaseDatabase.getInstance(
                "https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app"
            ).getReference("Danger")
            databaseD.child(serialNo.toString()).setValue(loc).addOnCompleteListener {
                dangerFlag=0
            }.addOnFailureListener{
            }
        }



        Log.i("location","location changed")
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
        Log.i("location","get location")
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 5f, this)
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


//    fun changeFragment(frag: Fragment){
//        val fragment = supportFragmentManager.beginTransaction()
//        fragment.replace(R.id.frame_container,frag).commit()
////        fragment.addToBackStack(frag)
//    }

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

    override fun onPause() {
        super.onPause()
        Log.i("status","Pause")
        getLocation()
    }


    override fun onResume() {
    super.onResume()
    Log.i("Flag", flag.toString())
    Log.i("status","RESUME")
    getLocation()
    }




}
