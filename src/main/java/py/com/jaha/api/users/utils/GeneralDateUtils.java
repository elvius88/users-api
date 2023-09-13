package py.com.jaha.api.users.utils;

import io.vavr.control.Try;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GeneralDateUtils {

    public static String formatTime(String time) {
        String timeStr = time;

        if (time != null && !time.contains(":")) {
            timeStr = String
                    .format("%-8s", time)
                    .replace(" ", "0");
            timeStr = timeStr.replaceFirst("([01]\\d|2[0123])([012345]\\d)([012345]\\d)?([012345]\\d)?", "$1:$2:$3.$4");
        }

        return timeStr;


    }

    public static LocalDateTime getLocalDateTime(java.sql.Date sqlDate, String time) {
        String dateStr = new SimpleDateFormat("ddMMyyyy").format(sqlDate);
        String timeStr = formatTime(time);

      return RepositoryHelper.getLocalDateTime(dateStr, timeStr);
    }

    public static String formatLocalDatetime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
    
    public static String formatDate(String dateFormat, String dateString) {
    	return Try.of(() -> {
    		Date date = new SimpleDateFormat("yyyyMMdd").parse(dateString);
        	SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
    		return formatter.format(date);
    	}).get();    	
    }
}
