package py.com.jaha.api.users.domain.models.users;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class State implements Serializable {

  private String id;
  private String name;
}
