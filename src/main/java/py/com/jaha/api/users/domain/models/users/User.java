package py.com.jaha.api.users.domain.models.users;

import java.io.Serializable;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

  private String id;
  private String email;
  private String locale;
  private Boolean isActive;
  private Boolean isDev;
  private Boolean isComplete;
  private String role;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}
