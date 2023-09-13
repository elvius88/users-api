package py.com.jaha.api.users.domain.commands.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import py.com.jaha.api.users.config.DtoMeta;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DtoMeta
public class GetUserResponse {

  private String id;
  private String email;
  private String locale;
  private Boolean isActive;
  private Boolean isDev;
  private Boolean isComplete;
  private String role;
}
