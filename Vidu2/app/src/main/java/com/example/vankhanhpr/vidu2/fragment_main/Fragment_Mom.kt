package com.example.vankhanhpr.vidu2.fragment_main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby.Create_File_Mon
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.json.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.location.Location
import android.widget.TextView
import android.widget.Toast


/**
 * Created by VANKHANHPR on 7/9/2017.
 */

class Fragment_Mom:Fragment()/*,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener*/
{
    /*private var location: Location? = null

    // Đối tượng tương tác với Google API
    private var gac: GoogleApiClient? = null

    // Hiển thị vị trí
    private var tvLocation: TextView? = null



    override fun onConnected(p0: Bundle?) {
        //getLocation()
    }

    override fun onConnectionSuspended(p0: Int) {
       //gac!!.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(context, "Lỗi kết nối: " + p0.getErrorMessage(), Toast.LENGTH_SHORT).show()
    }*/

    var call= Call_Receive_Server.getIns()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var k: View = inflater!!.inflate(R.layout.fragment_mom, container, false)
       // EventBus.getDefault().register(this)

        //gac!!.connect()
        //
        // buildGoogleApiClient()

        var bnt_create_file= k.findViewById(R.id.bnt_create_file)
        bnt_create_file.setOnClickListener()
        {
            var intent= Intent(context,Create_File_Mon::class.java)
            startActivity(intent)
        }
        return k
    }
    /*@Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getTemp() == AllValue.get_info_customer) {

        }
    }*/
    //lay thong tin ho so me va be
//    fun getInfor_file_mom_baby()
//    {
//        var array:Array<String>?= arrayOf(Json.AppLoginID)
//        call!!.CallEmit(AllValue.workername_create_file_mom,AllValue.servicename_create_file_mom,array!!,AllValue.get_info_customer.toString())
//    }
//
//    private fun getLocation() {
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // Kiểm tra quyền hạn
//            ActivityCompat.requestPermissions(context as Activity,
//                    arrayOf<String>(Manifest.permission.ACCESS_COARSE_LOCATION), 2)
//        } else {
//            location = LocationServices.FusedLocationApi.getLastLocation(gac)
//
//            if (location != null) {
//                val latitude = location!!.getLatitude()
//                val longitude = location!!.getLongitude()
//                // Hiển thị
//                tvLocation!!.setText(latitude.toString() + ", " + longitude)
//            } else {
//                tvLocation!!.setText("(Không thể hiển thị vị trí. " + "Bạn đã kích hoạt location trên thiết bị chưa?)")
//            }
//        }
//    }

    /**
     * Tạo đối tượng google api client
     */
    /*@Synchronized protected fun buildGoogleApiClient() {
        if (gac == null) {
            gac = GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build()
        }
    }*/

}