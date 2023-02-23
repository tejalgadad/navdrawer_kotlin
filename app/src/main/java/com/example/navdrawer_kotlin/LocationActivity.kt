package com.example.navdrawer_kotlin

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.navdrawer_kotlin.databinding.ActivityLocationBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.firebase.database.*


class LocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityLocationBinding
    private lateinit var databaseD : DatabaseReference
    private lateinit var databaseS : DatabaseReference
    private lateinit var databaseC : DatabaseReference
    private lateinit var fusedLocationClient:  FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private val LOCATION_PERMISSION_REQUEST =1
    var lat =0.0
    var long = 0.0
    var num :Long =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun getLocationAccess(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            mMap.isMyLocationEnabled = true
            getLocationUpdates()
            startLocationUpdates()
        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_REQUEST)
        }
    }

    private fun startLocationUpdates() {
         fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback,null)
    }

    private fun getLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest.interval = 30000
        locationRequest.fastestInterval =20000
        locationRequest.priority= LocationRequest.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                if(locationResult.locations.isNotEmpty()){
                    val location = locationResult.lastLocation
                    if(location!=null){
                        val latLng = LatLng(location.latitude,location.longitude)
//                        val markerOptions = MarkerOptions().position(latLng)
//                        mMap.addMarker(markerOptions)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20f))
                    }
                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==LOCATION_PERMISSION_REQUEST){
            if(grantResults.contains(PackageManager.PERMISSION_GRANTED)){
//                mMap.isMyLocationEnabled = true
                getLocationAccess()
            }
            else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLocationAccess()

        databaseS= FirebaseDatabase.getInstance("https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app"
        ).getReference("Serial")
        databaseD= FirebaseDatabase.getInstance("https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app"
        ).getReference("Danger")
        databaseS.child("number").get().addOnSuccessListener {
            num = it.value as Long
            for (i in 0..num){
                Log.e("num", num.toString())

                databaseD.child(i.toString()).get().addOnSuccessListener {
                    Log.e("success", "success")
                    if(it.child("longitude").value.toString()!="null"){
                        lat = it.child("latitude").value as Double
                        long = it.child("longitude").value as Double
                        Log.e("lat",it.child("latitude").toString())
                        Log.e("long",it.child("longitude").toString())
                        val dangerlocation = LatLng(lat, long)
                        mMap.addMarker(MarkerOptions().position(dangerlocation).title("Danger Loation").icon(
                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)))
                    }
                }
            }
        }





        // Add a marker in Sydney and move the camera
//        val zoomLevel =20f
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney").icon(
//            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
//        //.icon( BitmapDescriptorFactory.fromResource(R.drawable.marker)
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,zoomLevel))
    }
}
