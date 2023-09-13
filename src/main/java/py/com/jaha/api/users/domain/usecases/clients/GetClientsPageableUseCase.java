package py.com.jaha.api.users.domain.usecases.clients;

import static py.com.jaha.api.users.domain.models.commons.enums.ErrorCatalog.NOT_FOUND;
import static py.com.jaha.api.users.domain.usecases.utils.LogUtil.logAndThrows;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import py.com.jaha.api.users.domain.commands.clients.GetClientPageableResponse;
import py.com.jaha.api.users.domain.commands.clients.GetClientsCommand;
import py.com.jaha.api.users.domain.ports.in.clients.GetClientsPageablePort;
import py.com.jaha.api.users.domain.ports.out.ClientsRepositoryPort;
import py.com.jaha.api.users.domain.usecases.clients.mappers.GetClientResponseMapper;

@Slf4j
@RequiredArgsConstructor
public class GetClientsPageableUseCase implements GetClientsPageablePort {

  private final ClientsRepositoryPort clientsRepositoryPort;

  @Override
  public Page<GetClientPageableResponse> execute(GetClientsCommand command) {
    return Try.of(() -> clientsRepositoryPort.getPageableClientsBy(command.getName(), command.getLastname(), command.getEmail(), command.getId(), command.getPageable()))
        .filter(clientPage -> !CollectionUtils.isEmpty(clientPage.getContent()))
        .map(GetClientResponseMapper.INSTANCE::toGetClientsPageableResponse)
        .onSuccess(response -> log.debug("Query has been successful: [{}]", response))
        .onFailure(logAndThrows(log, "Error querying client data: [{}]", NOT_FOUND))
        .get();
  }

}
