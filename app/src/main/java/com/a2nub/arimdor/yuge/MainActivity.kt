package com.a2nub.arimdor.yuge

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var yugePhoto: ImageView
    private lateinit var lblCounter: TextView
    private lateinit var btnSensor: Button
    private val toasty: Toast by lazy { Toast.makeText(this, "YUGE!", Toast.LENGTH_SHORT) }
    private val toastySensor: Toast by lazy { Toast.makeText(this, "Sensor Changed!", Toast.LENGTH_SHORT) }

    private var counter = 0
    private var flagSensor = false

    // Sensor Vars
    private lateinit var mProximity: Sensor
    private lateinit var mSensorManager: SensorManager

    companion object {
        private const val SENSOR_SENSITIVITY = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        yugePhoto = findViewById(R.id.yugePhoto)
        btnSensor = findViewById(R.id.btnSensor)
        lblCounter = findViewById(R.id.lblCounter)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        volumeControlStream = AudioManager.STREAM_MUSIC

        yugePhoto.setOnClickListener { yuge() }
        btnSensor.setOnClickListener {
            flagSensor = true;
            toastySensor.show()
        }
    }

    private fun yuge() {
        toasty.show()
        val mPlayer = MediaPlayer.create(this, R.raw.yuge)
        mPlayer.start()
        counter++
        lblCounter.text = "You've Yuged $counter times"
        mPlayer.setOnCompletionListener { mPlayer ->
            mPlayer.release()
            toasty.cancel()
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            if (p0.sensor.type == Sensor.TYPE_PROXIMITY) {
                if (p0.values[0] >= -SENSOR_SENSITIVITY && p0.values[0] <= SENSOR_SENSITIVITY) {
                    if (flagSensor) yuge()
                }
            }
        }
    }
}
