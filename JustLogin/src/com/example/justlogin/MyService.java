package com.example.justlogin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//TODO do something useful
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
