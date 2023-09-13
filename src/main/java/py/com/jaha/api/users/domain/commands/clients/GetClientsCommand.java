package py.com.jaha.api.users.domain.commands.clients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetClientsCommand {

  private String id;
  private String name;
  private String lastname;
  private String email;
  private String city;
  private Pageable pageable;
}
