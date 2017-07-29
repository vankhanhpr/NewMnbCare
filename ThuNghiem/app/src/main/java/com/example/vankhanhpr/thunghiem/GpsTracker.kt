package com.example.vankhanhpr.thunghiem

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Debug
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import android.content.Context.LOCATION_SERVICE
import android.support.v4.app.ActivityCompat


/**
 * Created by VANKHANHPR on 7/27/2017.
 */
class GpsTracker(context: Context) : LocationListener
{

    var context: Context

    init {
        this.context= context
    }

    fun getLocation(): Location?
    {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),2)
            Log.e("fist", "error")
            return null
        }
        try {



            var lm = context.getSystemService(LOCATION_SERVICE) as LocationManager
            var isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)

            if (isGPSEnabled)
            {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6, 10f, this)
                val loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                return loc
            } else {
                Log.e("sec", "errpr")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }



    override fun onLocationChanged(location: Location) {}

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onProviderDisabled(provider: String) {}
}