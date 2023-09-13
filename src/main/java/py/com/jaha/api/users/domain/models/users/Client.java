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
public class Client implements Serializable {

  private String id;
  private String name;
  private String lastname;
  private String address;
  private Integer zip;
  private String phoneNumber;
  private String cellPhone;
  private String profileImage;
  private String profileMiniImage;
  private Integer amountEstablishments;
  private Integer amountBranches;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private City city;
  private User user;
}
