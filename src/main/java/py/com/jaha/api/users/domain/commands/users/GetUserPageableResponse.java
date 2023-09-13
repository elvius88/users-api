package py.com.jaha.api.users.domain.commands.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import py.com.jaha.api.users.config.DtoMeta;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DtoMeta
public class GetUserPageableResponse {

  private String id;
  private String email;
  private String locale;
  private Boolean isActive;
  private Boolean isDev;
  private Boolean isComplete;
  private String role;
}
