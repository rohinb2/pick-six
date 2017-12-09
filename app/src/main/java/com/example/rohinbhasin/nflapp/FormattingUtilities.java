package com.example.rohinbhasin.nflapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Class with methods for formatting Strings, dates, and other query parameters for the API.
 */
public class FormattingUtilities {

    private static final int YEAR_OFFSET = 1900;
    private static final int END_INDEX_FOR_DATE_FORMAT = 8;
    private static final int END_INDEX_FOR_DATE_DISPLAY = 10;
    private static final int END_INDEX_FOR_DAY = 3;

    /**
     * Gets a Date as a String.
     *
     * @param dateToFormat Date object of any date.
     * @return String: The date in format of a String YYYYMMDD.
     */
    public static String getDateAsString(Date dateToFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String formattedTime = dateFormat.format(dateToFormat);
        return formattedTime.substring(0, END_INDEX_FOR_DATE_FORMAT);
    }

    /**
     * Gets what day of the week it is (e.g. Mon, Tue, etc)
     *
     * @return int: an integer that represents what day of the week it is.
     */
    public static int getCurrentDayOfWeek() {

        final HashMap<String, Integer> DAYS_AS_INTEGERS = new HashMap<String, Integer>() {
            {
                put("Sun", 0);
                put("Mon", 1);
                put("Tue", 2);
                put("Wed", 3);
                put("Thu", 4);
                put("Fri", 5);
                put("Sat", 6);
            }
        };

        Calendar c = Calendar.getInstance();
        return DAYS_AS_INTEGERS.get(c.getTime().toString().substring(0, END_INDEX_FOR_DAY));
    }

    /**
     * Takes in a date and changes format of String to one that is printable.
     *
     * @param date String: Date in format YYYY-MM-DD
     * @return Reformatted String with day of week, month, and day as words.
     */
    public static String reformatDateForDisplay(String date) {
        String[] componentsOfDate = date.split("-");

        final int YEAR = Integer.valueOf(componentsOfDate[0]) - YEAR_OFFSET;
        final int MONTH = Integer.valueOf(componentsOfDate[1]) - 1;
        final int DAY = Integer.valueOf(componentsOfDate[2]);

        Date dateToFormat = new Date(YEAR, MONTH, DAY);
        return dateToFormat.toString().substring(0, END_INDEX_FOR_DATE_DISPLAY);
    }

    // Code from StackOverflow: https://stackoverflow.com/questions/3035692/how-to-convert-a-drawable-to-a-bitmap
    /**
     * Method to change a Drawable object into a Bitmap object.
     *
     * @param drawable a Drawable image
     * @return Bitmap: the Bitmap of the corresponding Drawable image.
     */
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
