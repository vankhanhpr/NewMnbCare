package com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby.map.DirectionFinder
import com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby.map.DirectionFinderListener
import com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby.map.Route
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import android.provider.Settings
import com.example.vankhanhpr.vidu2.fragment_main.fragment_bucking.Map_Location_Mom
import com.example.vankhanhpr.vidu2.getter_setter.Json

/**
 * Created by VANKHANHPR on 7/27/2017.
 */
class MyFragment : Fragment(), OnMapReadyCallback, LocationListener, DirectionFinderListener {
    var mMap: GoogleMap?= null
    var mMapView: MapView?=null
    var kinhdo : Double? = null
    var vido : Double? = null

    var lat:String?=""
    var lon:String?=""

    var MarkerPoints: ArrayList<LatLng>? = ArrayList()
    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var view1= inflater!!.inflate(R.layout.fragment_map, container, false)
        mMapView = view1!!.findViewById(R.id.map1)as MapView
        lat = Json.doctor!!.getC13()
        lon = Json.doctor!!.getC14()
        return view1
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(mMapView!=null)
        {
            lat = Json.doctor!!.getC13()
            lon = Json.doctor!!.getC14()
            mMapView!!.onCreate(null)
            mMapView!!.onResume()
            mMapView!!.getMapAsync(this)
        }
    }
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMapReady(googleMap: GoogleMap?)
    {
        try {

            mMap = googleMap
            //.............................................................. lấy tọa độ của tôi
            var locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            btnmylocation(mMap!!, locationManager)
            on_off_GPS(locationManager)
            locationManager!!.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0f, this)

            Log.d("toado", kinhdo.toString() + "    " + vido.toString())
            if (kinhdo != null && vido != null) {
                director_myLocation_to_B(kinhdo!!, vido!!)
            }
        }
        catch (e:Exception){}

    }
    private fun btnmylocation(mMap: GoogleMap,locationManager: LocationManager){
        mMap.getUiSettings().setZoomControlsEnabled(true)
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true)
        }
        mMap.setOnMyLocationButtonClickListener {
            on_off_GPS(locationManager)
            false
        }
    }

    private fun on_off_GPS(locationManager : LocationManager){
        if ( locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
        }
        else{ //...... show dialog khi dang tắt
            var dialog : Dialog = Dialog(context)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setContentView(R.layout.dialog_seting_location)
            var tv_cancel= dialog!!.findViewById(R.id.tv_cancel_location) as TextView
            var tv_agree= dialog!!.findViewById(R.id.tv_agree_location) as TextView
            dialog.show()
            tv_agree.setOnClickListener {
                startActivityForResult( Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0)
                dialog.cancel()
            }
            tv_cancel.setOnClickListener {
                dialog.cancel()
            }
        }
    }



    fun director_myLocation_to_B(latitudeA : Double,longtitudeA : Double){
        val startLocation = LatLng(latitudeA,longtitudeA)
        MarkerPoints!!.add(startLocation)
        val options = MarkerOptions()
        options.position(startLocation)
        options.title("A start")
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mMap!!.addMarker(options)

        Log.d("toadobs",lat+lon)
        var endLocation = LatLng(lat!!.toDouble(),lon!!.toDouble())

        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(endLocation, 20F))
        //Log.d("QQQQQ",MarkerPoints!!.get(0).latitude.toString())
        MarkerPoints!!.add(endLocation)
        val options2 = MarkerOptions()
        options2.position(endLocation)
        options2.title("B end")
        options2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        mMap!!.addMarker(options2)//........ them vao moi hien thi ?

        DirectionFinder(this,
                MarkerPoints!!.get(0).latitude.toString() + ","+MarkerPoints!!.get(0).longitude.toString(),
                lat.toString()+ "," +lon.toString()).execute()

    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}
    override fun onProviderEnabled(p0: String?) {}
    override fun onProviderDisabled(p0: String?) {}

    var chay2 : Int = 0
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onLocationChanged(location: Location?) {
        kinhdo = location!!.getLatitude()
        vido = location!!.getLongitude()
        //........................ load lai ban do lan 2
        if(kinhdo != null && vido != null && chay2 == 0){
            chay2 = 1
            onMapReady(mMap)
        }
    }



    override fun onDirectionFinderStart() {}
    override fun onDirectionFinderSuccess(route: MutableList<Route>?) {//..... lấy giá trị
        var polylinePaths = ArrayList<Polyline>()

        val polylineOptions = PolylineOptions().geodesic(true).color(Color.BLUE).width(10f)

        for (i in 0..route!![0].points.size - 1)
            polylineOptions.add(route!![0].points.get(i))

        polylinePaths.add(mMap!!.addPolyline(polylineOptions
                .width(10f)
                .color(Color.RED)))
    }

}