package com.tyrion.proximity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{

	SensorManager proximitySensorManager;
	Sensor proximitySensor;
	float maxSensorRange;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        proximitySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = proximitySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    
        maxSensorRange = proximitySensor.getMaximumRange();
    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

		Toast t = Toast.makeText(getApplicationContext(), "Accuracy Changed "+accuracy , Toast.LENGTH_SHORT);
		t.show();	
	
	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		Toast t = Toast.makeText(getApplicationContext(), event.values[0]+"" , Toast.LENGTH_SHORT);
		t.show();	
		
	}
	
	@Override
	  protected void onResume() {
	    // Register a listener for the sensor.
	    super.onResume();
	    proximitySensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
	  
	}

	  @Override
	  protected void onPause() {
	    // Be sure to unregister the sensor when the activity pauses.
	    super.onPause();
	    proximitySensorManager.unregisterListener(this);
	    
	  }
	
}
