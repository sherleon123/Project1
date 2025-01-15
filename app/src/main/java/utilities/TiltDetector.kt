package utilities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import interfaces.TiltCallback
import kotlin.math.abs

class TiltDetector(context: Context, private val tiltCallback: TiltCallback?) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) as Sensor
    private lateinit var sensorEventListener: SensorEventListener


    private var timestamp: Long = 0L

    init {
        initEventListener()
    }

    private fun initEventListener() {
        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                calculateTilt(x,y)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // pass
            }
        }
    }

    private fun calculateTilt(x: Float,y: Float) {
        if (System.currentTimeMillis() - timestamp >= 500) {//enough time passed since late check:
            timestamp = System.currentTimeMillis()
            if (x >= 2.0) {
                tiltCallback?.tiltLeft()
            }
            if (x <= -2.0) {
                tiltCallback?.tiltRight()
            }
            if (y >= 8.0) {
                tiltCallback?.tiltSlow()
            }
            if (y <= 0.0) {
                tiltCallback?.tiltFast()
            }
            }
    }

    fun start(){
        sensorManager
            .registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
    }



    fun stop(){
        sensorManager
            .unregisterListener(
                sensorEventListener,
                sensor
            )
    }

}