package py.com.jaha.api.users.domain.commands.clients;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import py.com.jaha.api.users.config.DtoMeta;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DtoMeta
public class GetClientsResponse {

  private List<GetClientResponse> clients;
}
