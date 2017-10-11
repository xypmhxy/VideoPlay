package com.video.ren.videoplay.utils;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import static android.provider.MediaStore.Images.ImageColumns.ORIENTATION;
import static android.view.OrientationEventListener.ORIENTATION_UNKNOWN;

/**
 * Created by Ren on 2017/10/11.
 * TODO
 */

public class ScreenUtils {
    public static final int LANDSCAPE = 0;
    public static final int PORTRAIT = 1;
    private static int orientationValue = -1;
    private static Sensor sensor;

    private static SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            int orientation = ORIENTATION_UNKNOWN;
            float X = -values[0];
            float Y = -values[1];
            float Z = -values[2];
            float magnitude = X * X + Y * Y;
            // Don't trust the angle if the magnitude is small compared to the y
            // value
            if (magnitude * 4 >= Z * Z) {
                // 屏幕旋转时
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                orientation = 90 - (int) Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }
            if (orientation > 225 && orientation < 315) {// 检测到当前实际是横屏
                if (orientationValue != LANDSCAPE)
                    mListener.onOrientationChanged(LANDSCAPE);
                orientationValue = LANDSCAPE;
            } else if ((orientation > 315 && orientation < 360) || (orientation > 0 && orientation < 45)) {// 检测到当前实际是竖屏
                if (orientationValue != PORTRAIT)
                    mListener.onOrientationChanged(PORTRAIT);
                orientationValue = PORTRAIT;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public static void registerSensorListener(Context context, onOrientationChangedListener listener) {
        SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sm == null) {
            Toast.makeText(context, "您的手机暂不支持重力感应功能", Toast.LENGTH_SHORT).show();
            return;
        }
        mListener = listener;
        sensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sm.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public static void unRegisterSensorListener(Context context) {
        SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sm == null)
            return;
        sm.unregisterListener(sensorEventListener, sensor);
    }

    private static onOrientationChangedListener mListener;

    public interface onOrientationChangedListener {
        void onOrientationChanged(int orientation);
    }
}
