package com.example.team919.rechtslinks;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    int flag = 0;
    int rechts = 0;
    int links = 0;
    double tres = 0.5;

    TextView txtRichtung;
    TextView txtGyro;
    TextView txtAnzRechts;
    TextView txtAnzLinks;
    EditText editTreshold;

    SensorManager sensorManager;
    DecimalFormat decimalFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTreshold = findViewById(R.id.editTreshold);
        editTreshold.setText(String.valueOf(tres));

        txtRichtung = findViewById(R.id.txtRichtung);
        txtGyro = findViewById(R.id.txtGyro);
        txtAnzRechts = findViewById(R.id.txtAnzRechts);
        txtAnzRechts.setText("Rechts:\n" +rechts);
        txtAnzLinks = findViewById(R.id.txtAnzLinks);
        txtAnzLinks.setText("Links:\n" + links);



        decimalFormat = new DecimalFormat("00.00");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        final SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                switch (event.sensor.getType()) {
                    case Sensor.TYPE_GYROSCOPE:                 // Z Achse (event.values[2]) auslesen
                        if(Math.abs(event.values[2]) >= tres){   //treshold bestimmen
                            if(event.values[2]>0){
                                txtRichtung.setText("<");
                                flag=1;
                                txtAnzLinks.setText("Links:\n" + links);
                            }
                            if(event.values[2]<0) {
                                txtRichtung.setText(">");
                                flag=2;
                                txtAnzRechts.setText("Rechts:\n" + rechts);
                            }
                        }else{
                            txtRichtung.setText("|");
                            if(flag == 1){
                                links++;
                                flag =0;
                            }
                            if(flag == 2){
                                rechts++;
                                flag =0;
                            }
                        }
                        break;
                }
                txtGyro.setText("X: " + decimalFormat.format(event.values[0])  + "\tY: " + decimalFormat.format(event.values[1]) + "\tZ: " + decimalFormat.format(event.values[2]));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_FASTEST);


        editTreshold.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tres = Double.valueOf(editTreshold.getText().toString());
            }
        });

    }
}
