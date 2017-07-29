package com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby.map
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.util.Log


/**
 * Created by VANKHANHPR on 7/27/2017.
 */
class GpsTracker(context: Context):LocationListener
{
    var context: Context? = null

    init {
        this.context = context
    }

    fun getLocation(): Location? {
        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("fist", "error")
            return null
        }
        try {
            var lm = context!!.getSystemService(LOCATION_SERVICE) as LocationManager

            var isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (isGPSEnabled) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6, 5f, this)
                var loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
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