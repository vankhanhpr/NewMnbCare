package com.example.vankhanhpr.vidu2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.vankhanhpr.vidu2.json.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by VANKHANHPR on 7/4/2017.
 */

public class Test extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        EventBus.getDefault().register(this);


    }
    @Subscribe
    public  void onEvent(MessageEvent event)
    {
        Log.d("asdasd ",event.getTemp().toString());
    }
}
