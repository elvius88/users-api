package py.com.jaha.api.users.domain.usecases.utils;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static java.util.Objects.nonNull;
import static py.com.jaha.api.users.config.exception.ErrorHandler.internalError;
import static py.com.jaha.api.users.config.exception.ErrorHandler.notFound;
import static py.com.jaha.api.users.domain.models.commons.enums.GlobalErrorCodes.GLOBAL_UNEXPECTED_ERROR_CODE;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import py.com.jaha.api.users.config.exception.ApiException;
import py.com.jaha.api.users.domain.models.commons.enums.ErrorCatalog;

@UtilityClass
public class LogUtil {

  /**
   * Logger utility class.
   *
   * @param log  sl4f implementor
   * @param message message to display
   * @param errorCatalog errorCatalog code and description
   * @return Throwable
   */
  public static Consumer<Throwable> logAndThrows(Logger log, String message, ErrorCatalog errorCatalog) {
    return error -> Match(error).of(
        Case($(instanceOf(ApiException.class)), ae -> {
          log.error(message, ae.getMessage(), ae);
          throw ae;
        }),
        Case($(instanceOf(NoSuchElementException.class)), ae -> {
          log.error(message, ae.getMessage(), ae);
          throw notFound(errorCatalog.getCode(), errorCatalog.getDescription());
        }),
        Case($(), t -> {
          log.error(message, t.getMessage(), t);
          throw internalError(errorCatalog.getCode(), errorCatalog.getDescription());
        }));
  }

  /**
   * Gestion de errores incluido el desencriptado.
   *
   * @param throwable super clase de todos los errores
   * @return ApiException
   */
  public static ApiException buildErrorCommon(Logger log, Throwable throwable) {
    log.error("Error de ejecucion comun :{}", throwable.getMessage());
    return throwable instanceof ApiException
        ? (ApiException) throwable
        : new ApiException(
        GLOBAL_UNEXPECTED_ERROR_CODE,
        nonNull(throwable.getMessage())
            ? throwable.getMessage()
            : "No es posible obtener la informacion",
        "Error Interno",
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        false);
  }
}
