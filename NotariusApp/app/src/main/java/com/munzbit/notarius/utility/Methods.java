package com.munzbit.notarius.utility;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Ratufa.Manish on 8/7/2015.
 */
public class Methods {


    public static String convert12HourTo24Hour(String time) {

        Date date = null;

        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");

        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");

        try {
            date = parseFormat.parse(time);
            System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
        } catch (Exception e) {

        }
        return displayFormat.format(date);
    }

    public static String pad(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        } else {
            return "0" + String.valueOf(c);
        }
    }
}
