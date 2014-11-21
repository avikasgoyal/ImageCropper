package com.imageCropper.android.liabrary;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Set of utils for dimension and UI measurements.
 */
public class UiUtils {
    public static float dpToPixels(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int pixelsToDp(Context context, float pixels) {
        float density = context.getResources().getDisplayMetrics().densityDpi;
        return Math.round(pixels / (density / 160f));
    }

    public static int getScreenHeight(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getScreenWidth(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }

    static public float[] getCenterOfScreen(Context context) {
        float[] center = new float[2];
        center[0] = getScreenHeight(context) / 2;
        center[1] = getScreenWidth(context) / 2;
        return center;
    }

}
