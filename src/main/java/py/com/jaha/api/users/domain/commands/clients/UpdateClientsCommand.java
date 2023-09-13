package py.com.jaha.api.users.domain.commands.clients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateClientsCommand {

  private String phoneNumber;
  private String name;
  private String lastname;
  private String city;
  private String state;
  private String zip;
  private String image;
  private String password;
  private String confirmPassword;
}
