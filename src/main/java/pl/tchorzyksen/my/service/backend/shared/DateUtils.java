package pl.tchorzyksen.my.service.backend.shared;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@UtilityClass
public class DateUtils {

  public static LocalDateTime convertToLocalDateTime(Date date){
    return date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
  }

}
