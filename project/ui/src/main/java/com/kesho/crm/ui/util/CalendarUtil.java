package com.kesho.crm.ui.util;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 10/13/13
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Helper functions for handling dates.
 */
public class CalendarUtil {

    /**
     * Default date format in the form 2013-03-18.
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Returns the given date as a well formatted string. The above defined date
     * format is used.
     *
     * @param date date to be returned as a string
     * @return formatted string
     */
    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMAT.format(date);
    }

    /**
     * Converts a String in the format "yyyy-MM-dd" to a Calendar object.
     *
     * Returns null if the String could not be converted.
     *
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public static Date parse(String dateString) {
        try {
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Checks the String whether it is a valid date.
     *
     * @param dateString
     * @return true if the String is a valid date
     */
    public static boolean validString(String dateString) {
        try {
            DATE_FORMAT.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
