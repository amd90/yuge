package amd.nrk.yuge;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.preference.Preference;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import amd.nrk.yuge.util.Preferences;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private ImageView yugeView;
    private TextView tvYugeCounter;
    private ImageView btnSensor, btnVersus;
    //private MediaPlayer mPlayer;
    private Toast toasty, toastySensor;
    private int counter;
    //private boolean force = true;


    //Shake variables

    //private boolean init;
    private Sensor mProximity;
    private SensorManager mSensorManager;
    private static final int SENSOR_SENSITIVITY = 4;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        yugeView = (ImageView) findViewById(R.id.yugeView);

        btnSensor = (ImageView) findViewById(R.id.btnSensor);

        if (Preferences.isForce(getApplicationContext())) {
            btnSensor.setColorFilter(getResources().getColor(R.color.colorWhite));
        } else {
            btnSensor.setColorFilter(getResources().getColor(R.color.colorGray));
        }
        tvYugeCounter = (TextView) findViewById(R.id.tvYugeCounter);
        //mPlayer = MediaPlayer.create(this, R.raw.yuge);
        counter = Preferences.getYugeCounter(this);

        tvYugeCounter.setText("You've Yuged " + counter + " times");
        toasty = Toast.makeText(MainActivity.this, "YUGE!", Toast.LENGTH_SHORT);
        yugeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yuge();

            }
        });
        btnSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sensorMessage;
                if (toastySensor != null) {
                    toastySensor.cancel();
                }
                if (Preferences.isForce(getApplicationContext())) {
                    sensorMessage = "Force Disabled";
                    btnSensor.setColorFilter(getResources().getColor(R.color.colorGray));
                    Preferences.setForce(getApplicationContext(), false);
                } else {
                    sensorMessage = "Force Enabled";
                    btnSensor.setColorFilter(getResources().getColor(R.color.colorWhite));
                    Preferences.setForce(getApplicationContext(), true);
                }

                toastySensor = Toast.makeText(MainActivity.this, sensorMessage, Toast.LENGTH_SHORT);
                toastySensor.show();
            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        btnVersus = (ImageView) findViewById(R.id.btnVersus);
        btnVersus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VersusActivity.class));
            }
        });
    }

    private void yuge() {
        toasty.show();
        MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.yuge);
        mPlayer.start();
        counter++;
        Preferences.setYugeCounter(getApplicationContext(), counter);
        tvYugeCounter.setText("You've Yuged " + counter + " times");
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mPlayer) {
                mPlayer.release();
                toasty.cancel();
            }
        });


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                if (Preferences.isForce(getApplicationContext())) yuge();
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
