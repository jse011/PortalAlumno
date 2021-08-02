package com.consultoraestrategia.ss_portalalumno.util;

import android.util.Log;

/**
 * Created by Hemant chand on 05/07/17.
 * Hex Opacity Values
 * 100% — FF
 * 95% — F2
 * 90% — E6
 * 85% — D9
 * 80% — CC
 * 75% — BF
 * 70% — B3
 * 65% — A6
 * 60% — 99
 * 55% — 8C
 * 50% — 80
 * 45% — 73
 * 40% — 66
 * 35% — 59
 * 30% — 4D
 * 25% — 40
 * 20% — 33
 * 15% — 26
 * 10% — 1A
 * 5% — 0D
 * 0% — 00
 */

public class ColorTransparentUtils {

    // This default color int
    public static final int defaultColorID = android.R.color.black;
    public static final String defaultColor = "000000";
    public static final String TAG = "ColorTransparentUtils";

    /**
     * This method convert numver into hexa number or we can say transparent code
     *
     * @param trans number of transparency you want
     * @return it return hex decimal number or transparency code
     */
    public static String convert(int trans) {
        String hexString = Integer.toHexString(Math.round(255 * trans / 100));
        return (hexString.length() < 2 ? "0" : "") + hexString;
    }

    public static String transparentColor(int colorCode, int trans) {
        return convertIntoColor(colorCode, trans);
    }


    /**
     * Convert color code into transparent color code
     *
     * @param colorCode color code
     * @param transCode transparent number
     * @return transparent color code
     */
    public static String convertIntoColor(int colorCode, int transCode) {
        // convert color code into hexa string and remove starting 2 digit

        String color = defaultColor;
        try{
            color = Integer.toHexString(colorCode).toUpperCase().substring(2);
        }catch (Exception ignored){}

        if (!color.isEmpty() && transCode < 100) {
            if (color.trim().length() == 6) {
                return "#" + convert(transCode) + color;
            } else {
                Log.d(TAG, "Color is already with transparency");
                return convert(transCode) + color;
            }
        }
        // if color is empty or any other problem occur then we return deafult color;
        return "#" + Integer.toHexString(colorCode).toUpperCase().substring(2);
    }
}
