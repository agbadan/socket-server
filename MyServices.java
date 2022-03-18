package com.mycompany.SocketServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Button;




import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
public class MyServices extends Service {





	public MyServices() {



	}





	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		onTaskRemoved(intent);
		
					   
					   
					   onCreate();
					   
					   
		return START_STICKY;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	@Override
	public void onTaskRemoved(Intent rootIntent) {
		Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
		restartServiceIntent.setPackage(getPackageName());
		startService(restartServiceIntent);
		super.onTaskRemoved(rootIntent);
	}
}
