package com.cis.kotlinaccelerometersensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

// 가속도 센서를 이용하면 기울기를 측정할 수 있다.
class MainActivity : AppCompatActivity() {
    var manager : SensorManager? = null
    var listener : SensorListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        listener = SensorListener()

        startBtn.setOnClickListener {
            var sensor = manager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            // SensorManager.SENSOR_DELAY_UI 를 이용하는게 가장 최적화된 딜레이 시간을 가질 수 있다.
            // 화면이 갱신 될 때마다 값을 가져오기 때문이다.
            var chk = manager?.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
            if (chk == false) {
                tv.text = "가속도 센서를 지원히지 않습니다."
            }
        }

        stopBtn.setOnClickListener {
            manager?.unregisterListener(listener)
        }
    }

    inner class SensorListener : SensorEventListener {

        // 센서 감도가 변경되었을 때 호출되는 메소드
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        // 값이 변경되었을 때 호출되는 메소드
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                tv.text = "x 축 : ${event?.values[0]}\n"
                tv.append("y 축 : ${event?.values[1]}\n")
                tv.append("z 축 : ${event?.values[2]}\n")
            }
        }
    }
}
