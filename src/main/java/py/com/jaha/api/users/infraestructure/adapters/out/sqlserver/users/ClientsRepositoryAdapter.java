package py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users;

import io.vavr.control.Try;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import py.com.jaha.api.users.domain.models.users.Client;
import py.com.jaha.api.users.domain.ports.out.ClientsRepositoryPort;
import py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users.mapper.ClientsMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientsRepositoryAdapter implements ClientsRepositoryPort {

  private final ClientsRepository clientsRepository;

  @Override
  public Client getClientById(String id) {
    return Try.of(() -> clientsRepository.findById(id))
        .map(Optional::get)
        .map(ClientsMapper.INSTANCE::toDomain)
        .get();
  }

  @Override
  public List<Client> getClientsBy(String name, String lastname, String email, String city) {
    return Try.of(() -> clientsRepository.findClientsBy(name, city))
        .map(ClientsMapper.INSTANCE::toDomainList)
        .get();
  }

  @Override
  public Page<Client> getPageableClientsBy(String name, String lastname, String email, String city, Pageable pageable) {
    return Try.of(() -> clientsRepository.findPageableClientsBy(name, city, pageable))
        .map(ClientsMapper.INSTANCE::toClientPageableResponse)
        .get();
  }
}
