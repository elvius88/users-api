package py.com.jaha.api.users.domain.usecases.clients;

import static py.com.jaha.api.users.domain.models.commons.enums.ErrorCatalog.NOT_FOUND;
import static py.com.jaha.api.users.domain.usecases.utils.LogUtil.logAndThrows;

import io.vavr.control.Try;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import py.com.jaha.api.users.domain.commands.clients.GetClientResponse;
import py.com.jaha.api.users.domain.commands.clients.GetClientsCommand;
import py.com.jaha.api.users.domain.ports.in.clients.GetClientPort;
import py.com.jaha.api.users.domain.ports.out.ClientsRepositoryPort;
import py.com.jaha.api.users.domain.usecases.clients.mappers.GetClientResponseMapper;

@Slf4j
@RequiredArgsConstructor
public class GetClientUseCase implements GetClientPort {

  private final ClientsRepositoryPort clientsRepositoryPort;

  @Override
  public GetClientResponse execute(GetClientsCommand command) {
    return Try.of(() -> clientsRepositoryPort.getClientById(command.getId()))
        .filter(Objects::nonNull)
        .map(GetClientResponseMapper.INSTANCE::toGetClientResponse)
        .onSuccess(response -> log.debug("Query has been successful: [{}]", response))
        .onFailure(logAndThrows(log, "Error querying client data by branch: [{}]", NOT_FOUND))
        .get();
  }
}
