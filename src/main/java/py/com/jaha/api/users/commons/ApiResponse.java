package py.com.jaha.api.users.commons;

import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import py.com.jaha.api.users.config.DtoMeta;

@DtoMeta
@NoArgsConstructor
@Data
public class ApiResponse<T> {

  private T data;

  public static <T> ApiResponse<T> of(T data) {
    ApiResponse<T> response = new ApiResponse<>();
    response.setData(data);
    return response;
  }

  public static <T> ApiResponse<T> of(Optional<T> data) {
    ApiResponse<T> response = new ApiResponse<>();
    data.ifPresent(response::setData);
    return response;
  }

  public static <T> ApiResponse<T> of(Optional<T> data, HttpServletResponse response) {
    if (data.isEmpty()) {
      response.setStatus(HttpStatus.NOT_FOUND.value());
      return null;
    } else {
      ApiResponse<T> res = new ApiResponse<>();
      res.setData(data.get());
      return res;
    }
  }
}
