package com.example.vankhanhpr.thunghiem

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), 123)
        khanh.setOnClickListener({

        var gt = GpsTracker(applicationContext)
        var l = gt.getLocation()
        if (l == null) {
            Toast.makeText(applicationContext, "GPS unable to get Value", Toast.LENGTH_SHORT).show()
        } else {
            var lat = l.latitude
            var lon = l.longitude
            Toast.makeText(applicationContext, "GPS Lat = $lat\n lon = $lon", Toast.LENGTH_SHORT).show()
        }

        })
    }
}
