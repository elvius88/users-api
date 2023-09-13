package py.com.jaha.api.users.utils;

import static java.util.Objects.nonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryHelper {

  private static final Logger log = LoggerFactory.getLogger(RepositoryHelper.class);

  public RepositoryHelper() {
  }

  public static LocalDateTime getLocalDateTime(String date, String time, String datePattern) {
    LocalDate localDate = getLocalDate(date, datePattern);
    LocalTime localTime = getLocalTime(time);

    return LocalDateTime.of(localDate, localTime);
  }

  public static LocalDateTime getLocalDateTime(String date, String time) {
    LocalDate localDate = getLocalDate(date);
    LocalTime localTime = getLocalTime(time);

    return LocalDateTime.of(localDate, localTime);
  }

  private static LocalTime getLocalTime(String time) {
    return null;
  }

  private static LocalDate getLocalDate(String date) {
    return getLocalDate(date, "ddMMyyyy");
  }

  private static LocalDate getLocalDate(String date, String pattern) {
    LocalDate response = null;
    if(date != null) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
      TemporalAccessor parse = formatter.parse(date);
      response = LocalDate.from(parse);
    }

    return response;
  }

  private static BigDecimal getBigDecimal(String decimal) {
    BigDecimal response = null;
    if(decimal != null) {
      response = new BigDecimal(formatOutputNumber(decimal));
    }

    return response;
  }

  private static String formatOutputNumber(String decimal) {
    try {
      BigDecimal num = new BigDecimal(decimal);
      decimal = num.toPlainString();
    } catch (Exception ex) {
      return decimal;
    }

    if (decimal != null && !decimal.trim().isEmpty()) {
      return !decimal.contains(".") && !decimal.contains("E") ? decimal + ".00" : decimal;
    } else {
      return null;
    }
  }

  public static <T extends Number> T getSafeNumber(String number, Class<T> numberType) {
    Number response = null;

    try {
      if (nonNull(number) && nonNull(numberType)) {
        if (numberType.isAssignableFrom(Integer.class)) {
          response = Integer.valueOf(number);
        } else if (numberType.isAssignableFrom(Long.class)) {
          response = Long.valueOf(number);
        } else if (numberType.isAssignableFrom(Double.class)) {
          response = Double.valueOf(number);
        } else if (numberType.isAssignableFrom(Float.class)) {
          response = Float.valueOf(number);
        }
      }
    } catch (NumberFormatException ex) {
      log.debug("Error al intentar convertir el valor: " + number);
    }

    return (T) response;
  }
}
