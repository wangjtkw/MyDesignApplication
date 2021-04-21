package com.example.mydesignapplication.netty

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.mydesignapplication.constants.HOST
import com.example.mydesignapplication.constants.PORT

class NettyService : Service() {
    private val TAG = "NettyService"


    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        connect()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun connect() {
        Log.d(TAG,"run")
        NettyClient.connect(HOST, PORT)
    }
}