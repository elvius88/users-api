package py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Clients implements Serializable {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(nullable = false, updatable = false)
  private String id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "lastname", nullable = false)
  private String lastname;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "zip", nullable = false)
  private Integer zip;

  @Column(name = "phone_number", nullable = false, updatable = false)
  private String phoneNumber;

  @Column(name = "cell_phone", nullable = false, updatable = false)
  private String cellPhone;

  @Column(name = "profile_img", nullable = false, updatable = false)
  private String profileImage;

  @Column(name = "profile_mini_img", nullable = false, updatable = false)
  private String profileMiniImage;

  @Column(name = "amount_establishments", nullable = false, updatable = false)
  private Integer amountEstablishments;

  @Column(name = "amount_branches", nullable = false, updatable = false)
  private Integer amountBranches;

  @Column(name = "created_at", nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name="city_id", nullable=false)
  private Cities city;

  @ManyToOne
  @JoinColumn(name="user_id", nullable=false)
  private Users user;
}
