package py.com.jaha.api.users.domain.commands.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUsersCommand {

  private String id;
  private String name;
  private String latitude;
  private String longitude;
  private String categoryId;
  private String establishmentId;
  private Pageable pageable;
}
