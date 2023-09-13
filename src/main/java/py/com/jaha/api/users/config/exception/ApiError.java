package py.com.jaha.api.users.config.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Data
public class ApiError {

  private List<ApiMessage> messages = new ArrayList<>();

  public void addError(ApiMessage em) {
    this.messages.add(em);
  }

  public String toString() {
    return "ApiError(messages=" + this.getMessages() + ")";
  }
}
