package com.example.vankhanhpr.vidu2.json.network

import android.widget.Toast
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server


/**
 * Created by VANKHANHPR on 7/5/2017.
 */
class ConnectivityReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent)
    {

        Toast.makeText(context, "Network is turned ON/OF", Toast.LENGTH_SHORT).show()
        var s = Call_Receive_Server.instance
        s.Sevecie()
    }
}
