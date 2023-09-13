package py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users.entities.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, String> {


  @Query("SELECT u FROM Users u " +
      "WHERE (:email IS NULL OR u.email LIKE %:email%) " +
      "ORDER BY u.createdAt")
  List<Users> findUsersBy(@Param("email") String email);

  @Query(value = "SELECT u FROM Users u " +
      "WHERE (:email IS NULL OR u.email LIKE %:email%)",
      countQuery = "SELECT COUNT(u.id) FROM Users u " +
          "WHERE (:email IS NULL OR u.email LIKE %:email%)")
  Page<Users> findPageableUsersBy(@Param("email") String email,
                                  Pageable pageable);
}

