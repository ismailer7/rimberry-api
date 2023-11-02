package io.idev.storeapi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	static SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	
	public static long diff(Date start, Date end) throws ParseException {
        long diff = end.getTime() - start.getTime();
        return diff;
	}
}
