package py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users implements Serializable {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(nullable = false, updatable = false)
  private String id;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "email_verified_at", nullable = false)
  private OffsetDateTime emailVerifiedAt;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "locale", nullable = false)
  private String locale;

  @Column(name = "is_active", nullable = false, updatable = false)
  private Boolean isActive;

  @Column(name = "is_dev", nullable = false, updatable = false)
  private Boolean isDev;

  @Column(name = "is_complete", nullable = false, updatable = false)
  private Boolean isComplete;

  @Column(name = "role", nullable = false, updatable = false)
  private String role;

  @Column(name = "remember_token", nullable = false, updatable = false)
  private String rememberToken;

  @Column(name = "created_at", nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  @OneToMany(mappedBy="user")
  private List<Clients> clients;

}
