package py.com.jaha.api.users.config.exception;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import py.com.jaha.api.users.constants.GlobalConstants;
import py.com.jaha.api.users.constants.GlobalErrorCodes;


@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Throwable.class)
  public @ResponseBody ApiError handleGlobalException(HttpServletRequest request, HttpServletResponse response, Throwable e) {
    log.error("Manejando error en GlobalHandler", e);

    request.setAttribute(GlobalConstants.ATTRIBUTE_EXCEPTION, e);

    ApiError eb = new ApiError();
    if (e instanceof ApiException) {

      ApiException ae = (ApiException) e;
      ApiMessage errorMessage = ae.toErrorMessage();
      eb.addError(errorMessage);

      if (Objects.nonNull(ae.getHttpStatus())){
        response.setStatus(ae.getHttpStatus());
      } else if (ae.getType() != null) { //just for backwards compatibility
        switch (ae.getType()) {
          case AUTHENTICATION:
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            break;
          case SYSTEM:
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            break;
          case SECURITY:
            response.setStatus(HttpStatus.FORBIDDEN.value());
            break;
          default:
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            break;
        }
      } else {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      }

    }else if (e instanceof MethodArgumentNotValidException){
      //when validation on an argument annotated with @Valid fails
      MethodArgumentNotValidException manve = (MethodArgumentNotValidException)e;
      BindingResult result = manve.getBindingResult();
      List<FieldError> fieldErrors = result.getFieldErrors();
      ApiMessage message = new ApiMessage();
      message.setCode(GlobalErrorCodes.GLOBAL_INVALID_PARAMETER_ERROR_CODE);
      message.setMessage(String.format("Body with error. %s", getBodyErrors(fieldErrors)));
      message.setUseApiMessage(false);
      message.setType(ApiMessageTypes.ERROR);

      eb.addError(message );
      response.setStatus(HttpStatus.BAD_REQUEST.value());

    } else if (e instanceof MissingServletRequestParameterException) {
      MissingServletRequestParameterException msrpe = (MissingServletRequestParameterException) e;

      ApiMessage message = new ApiMessage();
      message.setCode(GlobalErrorCodes.GLOBAL_MISSING_PARAMETER_ERROR_CODE);
      message.setMessage(String.format("Parameter [%s] of type [%s] is missing from request", msrpe.getParameterName(), msrpe.getParameterType()));
      message.setUseApiMessage(false);
      message.setType(ApiMessageTypes.ERROR);

      eb.addError(message );
      response.setStatus(HttpStatus.BAD_REQUEST.value());
    } else if (e instanceof HttpMessageNotReadableException){
      HttpMessageNotReadableException mnre = (HttpMessageNotReadableException)e;

      ApiMessage message = new ApiMessage();
      message.setCode(GlobalErrorCodes.GLOBAL_MISSING_PARAMETER_ERROR_CODE);
      message.setMessage("RequestBody requerido o invalido");
      message.setUseApiMessage(false);
      message.setType(ApiMessageTypes.ERROR);

      eb.addError(message );
      response.setStatus(HttpStatus.BAD_REQUEST.value());
    }else if (e instanceof HttpRequestMethodNotSupportedException) {
      HttpRequestMethodNotSupportedException mnse = (HttpRequestMethodNotSupportedException)e;

      ApiMessage message = new ApiMessage();
      message.setCode(GlobalErrorCodes.ERROR_HTTP_NOT_FOUND);
      message.setMessage(String.format("Path Invalido [%s%s]",mnse.getMethod(), request.getRequestURI()));
      message.setUseApiMessage(false);
      message.setType(ApiMessageTypes.ERROR);

      eb.addError(message );
      response.setStatus(HttpStatus.NOT_FOUND.value());
    } else {
      ApiMessage errorMessage = new ApiMessage();
      errorMessage.setCode(GlobalErrorCodes.GLOBAL_UNEXPECTED_ERROR_CODE);
      errorMessage.setMessage("Ocurrió un error inesperado. Por favor vuelva a intentarlo en unos minutos.");
      errorMessage.setType(ApiMessageTypes.ERROR);
      eb.addError(errorMessage);
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    log.debug("Error response: " + eb + " en " + request.getRequestURI());
    return eb;
  }

  private String getBodyErrors(List<FieldError> fieldErrors) {
    StringBuilder errors = new StringBuilder();

    for (FieldError fieldError : fieldErrors) {
      //String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
      errors.append("Atributo [").append(fieldError.getField()).append("] : [").append(fieldError.getDefaultMessage()).append("]. ");
    }

    return errors.toString();
  }

}
