package py.com.jaha.api.users.commons;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import py.com.jaha.api.users.config.DtoMeta;

@Getter
@Setter
@DtoMeta
@ToString
public class ApiListResponse<T> extends ApiDtoResponse {

  private List<T> data;
  
  public static <T> ApiListResponse<T> of(List<T> data) {
    ApiListResponse<T> response = new ApiListResponse<>();
    response.setData(data);
    return response;
  }

  public static <T> ApiListResponse<T> ofSingle(T el) {
    ApiListResponse<T> response = new ApiListResponse<>();
    response.setData(Collections.singletonList(el));
    return response;
  }

  public static <T> ApiListResponse<T> of(Optional<List<T>> data) {
    ApiListResponse<T> response = new ApiListResponse<>();
    data.ifPresent(dt -> response.setData(dt));
    return response;
  }

  public static <T> ApiListResponse<T> ofSingle(Optional<T> el) {
    ApiListResponse<T> response = new ApiListResponse<>();
    el.ifPresent(dt -> response.setData(Collections.singletonList(dt)));
    return response;
  }
}
