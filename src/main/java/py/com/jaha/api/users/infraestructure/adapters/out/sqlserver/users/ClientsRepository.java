package py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users.entities.Clients;

@Repository
public interface ClientsRepository extends CrudRepository<Clients, String> {

  @Query("SELECT c FROM Clients c " +
      "WHERE (:name IS NULL OR c.name LIKE %:name%) " +
      "AND (:city IS NULL OR c.city.name like %:city%) " +
      "ORDER BY c.createdAt")
  List<Clients> findClientsBy(@Param("name") String name,
                              @Param("city") String city);
  
  @Query(value = "SELECT c FROM Clients c " +
      "WHERE (:name IS NULL OR c.name LIKE %:name%) " +
      "AND (:city IS NULL OR c.city.name like %:city%) ",
      countQuery = "SELECT COUNT(c.id) FROM Clients c " +
          "WHERE (:name IS NULL OR c.name LIKE %:name%) " +
          "AND (:city IS NULL OR c.city.name like %:city%) ")
  Page<Clients> findPageableClientsBy(@Param("name") String name,
                                      @Param("city") String city,
                                      Pageable pageable);
}

