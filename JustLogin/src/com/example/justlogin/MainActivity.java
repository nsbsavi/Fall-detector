package com.example.justlogin;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener,LocationListener{
	
	SessionManager session;
	Button button;
	private SensorManager sensorManager;
	private String number;
	private boolean color = false;
	private View view;
	private long lastUpdate;
	
	//location
	LocationManager locman;
	LocationListener loclis;
	double longitude; //= location.getLongitude();
	double latitude;// = location.getLatitude();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		locman = (LocationManager)getSystemService(LOCATION_SERVICE);
		locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		session = new SessionManager(getApplicationContext());
		
		button = (Button) findViewById(R.id.button1);
		Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
		 
		session.checkLogin();
		 
		HashMap<String, String> user = session.getUserDetails();
    
	    view = findViewById(R.id.textView1);
	    view.setBackgroundColor(Color.GREEN);
	    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    lastUpdate = System.currentTimeMillis();
	    
	    number = user.get(SessionManager.KEY_NUMBER);
	    
	    
	    button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// Clear the session data
				// This will clear all session data and 
				// redirect user to LoginActivity
				session.ChangeNumber();
			}
		});
	    Context context = getApplicationContext();
		Intent i= new Intent(context, MyService.class);
		context.startService(i); 
  	}
	
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
		      getAccelerometer(event);
		}		
	}
	
	public void getAccelerometer(SensorEvent event) {
		  SmsManager smsManager = SmsManager.getDefault();
		  float[] values = event.values;
	    // Movement
	    float x = values[0];
	    float y = values[1];
	    float z = values[2];

	    float accelationSquareRoot = (x * x + y * y + z * z)
	        / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
	    long actualTime = event.timestamp;
	    if (accelationSquareRoot >=3) //
	    {
	    	if (actualTime - lastUpdate < 200) {
	    		return;
	    	}
	    	lastUpdate = actualTime;
	        String text = "Help me!! http://maps.google.com/?q=";
	    	String locate_latitude = String.valueOf(latitude);//43.7920533400153,6.37761393942265";
	    	String comma = ",";
	    	String locate_longitude = String.valueOf(longitude);
	    	String Text = text + locate_latitude + comma + locate_longitude;
	    	
	    	smsManager.sendTextMessage(number, null, Text, null, null);
	    		      
	  	  	if (color) {
	  	  		view.setBackgroundColor(Color.GREEN);
	  	  	}
	  	  	else {
	  	  		view.setBackgroundColor(Color.RED);
	  	  	}
	  	  	color = !color;
	       
		    final Intent k = new Intent(Intent.ACTION_CALL);
			k.setData(Uri.parse("tel:"+number));
			
			Timer timer = new Timer();
	    	timer.schedule(new TimerTask() {
	    		@Override
		    	public void run() {		   
	    			startActivity(k);
		    	}
		    }, 60000);
	    }
	}
	
	 @Override
	  protected void onResume() {
	    super.onResume();
	    sensorManager.registerListener(this,
	        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_NORMAL);
	  }

	  @Override
	  protected void onPause() {
	    // unregister listener
	    super.onPause();
	    sensorManager.unregisterListener(this);
	  }


	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		longitude = location.getLongitude();
		latitude = location.getLatitude();
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Provider is disables", Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Provider is Enables", Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub	
	}
}
