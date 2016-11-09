package com.egco428.a13288;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class ShakeActivity extends AppCompatActivity implements SensorEventListener {

    boolean shakeCheck = false;
    private SensorManager sensorManager;
    private long lastUpdate;
    public static final String tag1 = "tag1";
    public static final String tag2= "tag2";
    public static final String tag3 = "tag3";
    ImageView closeCookies;
    ImageButton shakeBtn;
    int counter = 0,randomN;
    TextView datePicker;
    TextView result;

    final int[] imageId1 = {R.drawable.image1,R.drawable.image2,R.drawable.image3,
            R.drawable.image4,R.drawable.image5};
    String[] cookies_text ={"You will get A","You're Lucky","Don't Panic","Something surprise you today","Work Harder"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE); //prepare to connect with sensor
        lastUpdate = System.currentTimeMillis();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_bar1);

        ImageButton addBtn = (ImageButton)findViewById(R.id.backImgBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        shakeBtn = (ImageButton)findViewById(R.id.btnCircle);
        shakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shakeCheck = true;
                Toast.makeText(ShakeActivity.this, "Please shake your device 6 times to save", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (shakeCheck) {
                getAccelerometer(event);
            }
        }
    }

    private void getAccelerometer(SensorEvent event){
        float[] values = event.values;


        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];


        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();
        if (accelationSquareRoot > 2){
            if (actualTime - lastUpdate < 500) {
                return;
            }
            lastUpdate = actualTime;
            final int[] imageId = {R.drawable.shake,R.drawable.shaking,R.drawable.save};
            shakeBtn.setImageResource(imageId[1]);
            Random randomNumber = new Random();
            randomN = randomNumber.nextInt(imageId1.length);
            counter = counter + 1;
            Toast.makeText(ShakeActivity.this, "ShakeCount = " + counter, Toast.LENGTH_SHORT).show();
        }
        if(counter > 5){

            final int[] imageId = {R.drawable.shake,R.drawable.shaking,R.drawable.save};
            shakeBtn.setImageResource(imageId[2]);

            result = (TextView)findViewById(R.id.result);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
            final String DateTimeFormat = dateformat.format(calendar.getTime());

            datePicker = (TextView)findViewById(R.id.datePicker);

                closeCookies = (ImageView)findViewById(R.id.imageView2);
                closeCookies.setImageResource(imageId1[randomN]);
                result.setText(cookies_text[randomN]);
                datePicker.setText(DateTimeFormat);

            counter = 0;

            shakeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    String inputValue1 = cookies_text[randomN];
                    intent.putExtra(tag1,inputValue1);
                    String inputValue2 = DateTimeFormat;
                    intent.putExtra(tag2,inputValue2);
                    String inputValue3 = String.valueOf(randomN+1);
                    intent.putExtra(tag3,inputValue3);
                    setResult(ShakeActivity.RESULT_OK, intent);
                    finish();
                    return;
                }
            });
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener
                (this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this); //will not get value from phone
    }
}

