package py.com.jaha.api.users.config.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorHandler {

  public static ApiException badRequest(String code, String userMessage) {
    return new ApiException(code, userMessage, userMessage, BAD_REQUEST.value());
  }

  public static ApiException internalError(String code, String userMessage) {
    return new ApiException(code, userMessage, userMessage, INTERNAL_SERVER_ERROR.value());
  }

  public static ApiException unauthorized(String code, String userMessage) {
    return new ApiException(code, userMessage, userMessage, UNAUTHORIZED.value());
  }

  public static ApiException notFound(String code, String userMessage) {
    return new ApiException(code, userMessage, userMessage, NOT_FOUND.value());
  }
}
