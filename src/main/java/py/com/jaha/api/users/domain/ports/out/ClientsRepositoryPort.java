package py.com.jaha.api.users.domain.ports.out;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.jaha.api.users.domain.models.users.Client;

public interface ClientsRepositoryPort {

  Client getClientById(String id);

  List<Client> getClientsBy(String name, String lastname, String email, String city);

  Page<Client> getPageableClientsBy(String name, String lastname, String email, String city, Pageable pageable);
}
