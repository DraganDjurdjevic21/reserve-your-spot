package com.prodyna.reserveyourspot.commons;

public class Constants {
    // Prefix value to generate unique code
    public final static Long UNIQUE_CODE_PREF = 1001L;
    // Prefix value to generate unique code for work station
    public final static String WORK_STATION_UNIQUE_CODE_PREF = "PD";

    // type of office room for auto generate unique number
    public final static String OFFICE_ROOM_TYPE = "OFFICE_ROOM";
    // type of work station for auto generate unique number
    public final static String WORK_STATION_TYPE = "WORK_STATION";

    // Regex code for reservation date format
    public static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    // Reservation date format
    public final static String RESERVATION_DATE_FORMAT = "yyyy-MM-dd";
}
