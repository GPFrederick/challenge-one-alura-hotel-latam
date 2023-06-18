package utils;

import java.sql.Date;
import java.time.temporal.ChronoUnit;

public class Util {

	public static double priceCalculation(long days) {
    	return 59000.99 * days;
    }
	
	public static long numberOfDays(String checkIn, String checkOut) {
    	long numberOfDays = 0;
    	System.out.println(checkIn);
    	System.out.println(checkOut);
    	if (checkIn != null && checkOut != null) {
			Date checkInSQL = Date.valueOf(checkIn); 
			Date checkOutSQL = Date.valueOf(checkOut);
			numberOfDays = ChronoUnit.DAYS.between(checkInSQL.toLocalDate(), checkOutSQL.toLocalDate());	
    	}
    	return numberOfDays;
    }
}
