package com.tyrion.proximity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{

	SensorManager proximitySensorManager;
	Sensor proximitySensor;
	
	boolean validFirstTapFlag;
	
	float nearProximityValue;
	float farProximityValue;
	
	long firstProximityNear;
	long secondProximityNear;
	long secondProximityFar;
	long firstProximityFar;
	
	Timer tapDifferenceTimer;
	
	WakeLock mWakeLock;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        proximitySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = proximitySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		tapDifferenceTimer = new Timer("gapTimer", true);
		
        farProximityValue = proximitySensor.getMaximumRange();
        nearProximityValue = 0;

    	firstProximityNear = 0;
    	secondProximityNear = 0;
    	secondProximityFar = 0;
    	firstProximityFar = 0;
    	
    	validFirstTapFlag = false;

    	PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(pm.PARTIAL_WAKE_LOCK, "");
        mWakeLock.acquire();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if(event.values[0] == nearProximityValue) {

			System.out.println("near");
			
			if(!validFirstTapFlag) {
			
				firstProximityNear = event.timestamp;
			
			}
			else {
				
				secondProximityNear = event.timestamp;
								
			}
			
		}
		else if(event.values[0] == farProximityValue) {
			
			System.out.println("far");
			
			if(!validFirstTapFlag) {
				
				firstProximityFar = event.timestamp;
				
				if((firstProximityFar - firstProximityNear)/1000000000 < 1) {
				
					validFirstTapFlag = true;		
					
					tapDifferenceTimer.schedule( new TimerTask() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(validFirstTapFlag) {
								
								validFirstTapFlag = false;

								System.out.println("Single Tap");
							
							}
						}
					}, 2000);

					
				}

			}
			else {
				
				secondProximityFar = event.timestamp;
				
				try {
					tapDifferenceTimer.purge();
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				if((secondProximityFar - secondProximityNear)/1000000000 < 2) {
					
					validFirstTapFlag = false;
					System.out.println("Double Tap");
					
				}
					
			}
				
		}
	}
	
	@Override
	  protected void onResume() {
	    // Register a listener for the sensor.
	    super.onResume();
	    proximitySensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_FASTEST);
	  
	}

	  @Override
	  protected void onPause() {
	    // Be sure to unregister the sensor when the activity pauses.
	    super.onPause();
	    proximitySensorManager.unregisterListener(this);
	    
	  }
	
}
