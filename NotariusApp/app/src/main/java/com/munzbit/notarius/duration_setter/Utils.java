package com.munzbit.notarius.duration_setter;

import static java.lang.Math.abs;

import java.util.EnumMap;
import java.util.Map;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;
import android.graphics.Typeface;


public class Utils {

    public static final DateTimeFormatter SIMPLE_DATE_FORMAT_AM_PM = DateTimeFormat.forPattern("h");
    public static final DateTimeFormatter SIMPLE_DATE_FORMAT_MERIDIAN = DateTimeFormat.forPattern("a");
    public static final DateTimeFormatter SIMPLE_DATE_FORMAT_HOURS = DateTimeFormat.forPattern("H");

    public static boolean shouldMoveClockwise(int oldValue, int newValue) {
        int dist = abs(newValue - oldValue);
        int direction = oldValue < newValue ? 1 : -1;
        direction = dist < CircularClockSeekBar.TOTAL_DEGREES_INT / 2 ? direction : -direction;
        return direction == 1;
    }

    public static int getDistanceTo(int oldValue, int newValue) {
        int totalDegrees = CircularClockSeekBar.TOTAL_DEGREES_INT;
        boolean isClockWise = shouldMoveClockwise(oldValue, newValue);
        int dist = (newValue - oldValue) % totalDegrees;

        if (isClockWise) {
            if (abs(dist) > totalDegrees / 2) {
                dist = (totalDegrees - oldValue) + newValue;
            } else {
                dist = newValue - oldValue;
            }
        } else {
            if (abs(dist) > totalDegrees / 2) {
                dist = totalDegrees - (newValue - oldValue);
                dist = -dist;
            } else {
                dist = newValue - oldValue;
            }
        }
        return dist;
    }

    public static int getDelta(int oldDegrees, int newDegrees) {
        if ((oldDegrees == CircularClockSeekBar.TOTAL_DEGREES_INT && newDegrees == 0) || (newDegrees == CircularClockSeekBar.TOTAL_DEGREES_INT && oldDegrees == 0)
                || (oldDegrees == 0 && newDegrees == 0)) {
            // dont worry about delta for this condition as this basically means they are same.
            // we have this granular values when user touches/scrolls
            return 0;
        }
        return getDistanceTo(oldDegrees, newDegrees);
    }

    @SuppressWarnings("PublicInnerClass")
    public enum FontType {
        LIGHT,
        MEDIUM,
        REGULAR,
        BOLD
    }

    private static final Map<FontType, String> sFontMap = new EnumMap<FontType, String>(FontType.class);

    static {
        sFontMap.put(FontType.LIGHT, "Roboto-Light.ttf");
        sFontMap.put(FontType.MEDIUM, "Roboto-Medium.ttf");
        sFontMap.put(FontType.REGULAR, "Roboto-Regular.ttf");
        sFontMap.put(FontType.BOLD, "Roboto-Bold.ttf");
    }

    /**
     * Cache for loaded Roboto typefaces.
     */
    private static final Map<FontType, Typeface> sTypefaceCache = new EnumMap<FontType, Typeface>(FontType.class);

    /**
     * Creates and returns Roboto typeface and caches it.
     *
     * @param context  {@link Context} Context that will obtain resources.
     * @param fontType {@link
     * @return Returns the {@link Typeface} corresponding to the
     */
    public static Typeface getRobotoTypeface(Context context, FontType fontType) {
        String fontPath = sFontMap.get(fontType);
        if (!sTypefaceCache.containsKey(fontType)) {
            sTypefaceCache.put(fontType, Typeface.createFromAsset(context.getAssets(), fontPath));
        }
        return sTypefaceCache.get(fontType);
    }
}
