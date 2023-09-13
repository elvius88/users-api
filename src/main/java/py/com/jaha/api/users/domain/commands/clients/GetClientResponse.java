package py.com.jaha.api.users.domain.commands.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class GetClientResponse {

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "lastname")
  private String lastname;

  @JsonProperty(value = "email")
  private String email;

  @JsonProperty(value = "phone_number")
  private String phoneNumber;

  @JsonProperty(value = "city")
  private String city;

  @JsonProperty(value = "state")
  private String state;

  @JsonProperty(value = "zip")
  private Integer zip;
}
