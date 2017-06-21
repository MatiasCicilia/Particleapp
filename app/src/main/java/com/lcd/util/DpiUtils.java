package com.lcd.util;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by Matias Cicilia on 20-Jun-17.
 */

public class DpiUtils {

    public static int toPixels(int dp, DisplayMetrics metrics) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

}