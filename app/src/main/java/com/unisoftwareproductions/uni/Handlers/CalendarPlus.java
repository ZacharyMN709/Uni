package com.unisoftwareproductions.uni.Handlers;

import java.util.Calendar;

/**
 * Created by Zachary on 06-Sep-16.
 */
public class CalendarPlus {

    static Calendar c = Calendar.getInstance();
    static int Year = c.get(Calendar.YEAR);
    static int Month = c.get(Calendar.MONTH);
    static int Day = c.get(Calendar.DAY_OF_MONTH);
    static int DayName = c.get(Calendar.DAY_OF_WEEK);
    static int Hour12 = c.get(Calendar.HOUR);
    static int Hour24 = c.get(Calendar.HOUR_OF_DAY);
    static int Minute = c.get(Calendar.MINUTE);
    static int intAMPM = c.get(Calendar.AM_PM);
    static int Second = c.get(Calendar.SECOND);


    public static String getCurrentDateNum () {
        String CurrentDate = Year + "-" + Month + "-" + Day;
        return CurrentDate;
    }

// -------------------------------------------------------------------------------------------------

    public static String getDayNum() {
        return String.valueOf(Day);
    }

    public static String getDayShort() {
        String day;
        switch (Day) {
            case 1:
            case 21:
            case 31:
                day = String.valueOf(Day) + "st";
                break;
            case 2:
            case 22:
                day = String.valueOf(Day) + "nd";
                break;
            case 3:
            case 23:
                day = String.valueOf(Day) + "rd";
                break;
            default:
                day = String.valueOf(Day) + "th";
                break;
        }

        return day;
    }

    public static String getDayName() {
        String day = null;
        switch (DayName) {
            case 1: day = "Sunday"; break;
            case 2: day = "Monday"; break;
            case 3: day = "Tuesday"; break;
            case 4: day = "Wednesday"; break;
            case 5: day = "Thursday"; break;
            case 6: day = "Friday"; break;
            case 7: day = "Saturday"; break;
        }
        return day;
    }

// -------------------------------------------------------------------------------------------------

    public static String getMonthNum() {
        return String.valueOf(Month + 1);
    }

    public static String getMonthShort() {
        String month = null;
        switch (Month + 1) {
            case 1: month = "Jan"; break;
            case 2: month = "Feb"; break;
            case 3: month = "Mar"; break;
            case 4: month = "Apr"; break;
            case 5: month = "May"; break;
            case 6: month = "Jun"; break;
            case 7: month = "Jul"; break;
            case 8: month = "Aug"; break;
            case 9: month = "Sep"; break;
            case 10: month = "Oct"; break;
            case 11: month = "Nov"; break;
            case 12: month = "Dec"; break;
        }
        return month;
    }

    public static String getMonthLong() {
        String month = null;
        switch (Month + 1) {
            case 1: month = "January"; break;
            case 2: month = "February"; break;
            case 3: month = "March"; break;
            case 4: month = "April"; break;
            case 5: month = "May"; break;
            case 6: month = "June"; break;
            case 7: month = "July"; break;
            case 8: month = "August"; break;
            case 9: month = "September"; break;
            case 10: month = "October"; break;
            case 11: month = "November"; break;
            case 12: month = "December"; break;
        }
        return month;
    }

// -------------------------------------------------------------------------------------------------

    public static String getTime12hr() {
        if (Hour12 == 0) {Hour12 = 12;}
        String minute;
        switch (Minute) {
            case 0: minute = "00"; break;
            case 1: minute = "01"; break;
            case 2: minute = "02"; break;
            case 3: minute = "03"; break;
            case 4: minute = "04"; break;
            case 5: minute = "05"; break;
            case 6: minute = "06"; break;
            case 7: minute = "07"; break;
            case 8: minute = "08"; break;
            case 9: minute = "09"; break;
            default: minute = String.valueOf(Minute);
        }
        String AMPM;
        if (intAMPM == 0) {AMPM = "AM";} else {AMPM = "PM";}
        String time = Hour12 + ":" + minute + " " + AMPM;
        return time;
    }

    public static String getTime24hr() {
        String minute, hour;
        switch (Minute) {
            case 0: minute = "00"; break;
            case 1: minute = "01"; break;
            case 2: minute = "02"; break;
            case 3: minute = "03"; break;
            case 4: minute = "04"; break;
            case 5: minute = "05"; break;
            case 6: minute = "06"; break;
            case 7: minute = "07"; break;
            case 8: minute = "08"; break;
            case 9: minute = "09"; break;
            default: minute = String.valueOf(Minute);
        }
        switch (Hour24) {
            case 0: hour = "00"; break;
            case 1: hour = "01"; break;
            case 2: hour = "02"; break;
            case 3: hour = "03"; break;
            case 4: hour = "04"; break;
            case 5: hour = "05"; break;
            case 6: hour = "06"; break;
            case 7: hour = "07"; break;
            case 8: hour = "08"; break;
            case 9: hour = "09"; break;
            default: hour = String.valueOf(Hour24);
        }
        String time = hour + ":" + minute;
        return time;
    }

// -------------------------------------------------------------------------------------------------

    public static String getTimeStamp() {
        String timestamp = getMonthShort() + " " + Day + " " + getTime12hr();
        return timestamp;
    }

    public static String generateTimestamp(boolean year, String month, String day, String time) {
        String Tyear, Tmonth, Tday, Ttime;

        if (year) {Tyear = String.valueOf(Year);} else {Tyear = null;}

        switch (month) {
            case "Num": Tmonth = getMonthNum(); break;
            case "Short": Tmonth = getMonthShort(); break;
            case "Long": Tmonth = getMonthLong(); break;
            default: Tmonth = null; break;
        }

        switch (day) {
            case "Num": Tday = getDayNum(); break;
            case "Short": Tday = getDayShort(); break;
            case "Long": Tday = getDayName(); break;
            default: Tday = null; break;
        }

        switch (time) {
            case "12hr": Ttime = getTime12hr(); break;
            case "24hr": Ttime = getTime24hr(); break;
            default: Ttime = null; break;
        }

        String TIMESTAMP = Tyear + Tmonth + Tday + Ttime;
        return TIMESTAMP;
    }

}
