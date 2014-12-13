package com.example.leech_000.inf133project;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;


public class MainActivity extends Activity {

    private TextView myLabelX;
    private TextView myLabelY;
    private TextView myLabelZ;
    private float lastX;
    private float lastY;
    private float lastZ;
    private SensorManager GravitySensorManager;
    private SensorEventListener GravityListener;
    private MediaPlayer mp;
    private AssetFileDescriptor afd_captainFalcon1;
    private AssetFileDescriptor afd_mario1;
    private AssetFileDescriptor afd_marth1;
    private AssetFileDescriptor afd_marth2;
    private AssetFileDescriptor afd_ness1;
    private AssetFileDescriptor afd_ness2;
    private AssetFileDescriptor afd_pikachu1;
    private AssetFileDescriptor afd_toonlink1;
    private AssetFileDescriptor afd_yoshi1;


    private void updateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myLabelX.setText(" " + lastX);
                myLabelY.setText(" " + lastY);
                myLabelZ.setText(" " + lastZ);
            }
        });
    }


    synchronized void playAudio(AssetFileDescriptor afd){
        if (mp.isPlaying()){
            return;
        }
        else{
            try {
                mp.reset();
                mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("playAudio: ", "" + e + "\n adf: " + afd.toString());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        myLabelX = (TextView)findViewById(R.id.editTextX);
        myLabelY = (TextView)findViewById(R.id.editTextY);
        myLabelZ = (TextView)findViewById(R.id.editTextZ);
        GravitySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        GravityListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                lastX = values[0];
                lastY = values[1];
                lastZ = values[2];
                updateUI();

                if (Math.floor(lastX) == 9){
                    playAudio(afd_captainFalcon1);
                }
                else if (Math.ceil(lastX) == -9){
                    playAudio(afd_mario1);
                }
                else if (Math.ceil(lastY) == -9){
                    playAudio(afd_ness2);
                }
                else if (Math.floor(lastY) == 9){
                    playAudio(afd_toonlink1);
                }
                else if(Math.ceil(lastZ) == -9){
                    playAudio(afd_marth1);
                }
                else if(Math.floor(lastX) > 2 && Math.floor(lastX) < 5 && Math.floor(lastY) > 5 && Math.floor(lastY) < 7 && Math.floor(lastZ) > 5 && Math.floor(lastZ) < 7){
                    playAudio(afd_yoshi1);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };


        mp = new MediaPlayer();
        afd_captainFalcon1 = getApplicationContext().getResources().openRawResourceFd(R.raw.captainfalcon01);
        afd_mario1 = getApplicationContext().getResources().openRawResourceFd(R.raw.mario01);
        afd_marth1 = getApplicationContext().getResources().openRawResourceFd(R.raw.marth01);
        afd_marth2 = getApplicationContext().getResources().openRawResourceFd(R.raw.marth02);
        afd_ness1 = getApplicationContext().getResources().openRawResourceFd(R.raw.ness01);
        afd_ness2 = getApplicationContext().getResources().openRawResourceFd(R.raw.ness02);
        afd_pikachu1 = getApplicationContext().getResources().openRawResourceFd(R.raw.pikachu01);
        afd_toonlink1 = getApplicationContext().getResources().openRawResourceFd(R.raw.toonlink01);
        afd_yoshi1 = getApplicationContext().getResources().openRawResourceFd(R.raw.yoshi01);
    }

    @Override
    public void onResume(){
        super.onResume();
        GravitySensorManager.registerListener(GravityListener, GravitySensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onStop(){
        GravitySensorManager.unregisterListener(GravityListener);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
