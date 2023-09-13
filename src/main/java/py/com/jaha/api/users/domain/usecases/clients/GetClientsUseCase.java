package py.com.jaha.api.users.domain.usecases.clients;

import static py.com.jaha.api.users.domain.models.commons.enums.ErrorCatalog.NOT_FOUND;
import static py.com.jaha.api.users.domain.usecases.utils.LogUtil.logAndThrows;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import py.com.jaha.api.users.domain.commands.clients.GetClientsCommand;
import py.com.jaha.api.users.domain.commands.clients.GetClientsResponse;
import py.com.jaha.api.users.domain.ports.in.clients.GetClientsPort;
import py.com.jaha.api.users.domain.ports.out.ClientsRepositoryPort;
import py.com.jaha.api.users.domain.usecases.clients.mappers.GetClientResponseMapper;

@Slf4j
@RequiredArgsConstructor
public class GetClientsUseCase implements GetClientsPort {

  private final ClientsRepositoryPort clientsRepositoryPort;

  @Override
  public GetClientsResponse execute(GetClientsCommand command) {
    return Try.of(() -> clientsRepositoryPort.getClientsBy(command.getName(), command.getLastname(), command.getEmail(), command.getCity()))
        .filter(establishments -> !CollectionUtils.isEmpty(establishments))
        .map(GetClientResponseMapper.INSTANCE::toGetClientResponseList)
        .map(response -> GetClientsResponse.builder().build())
        .onSuccess(response -> log.debug("Query has been successful: [{}]", response))
        .onFailure(logAndThrows(log, "Error querying establishments data by criteria: [{}]", NOT_FOUND))
        .get();
  }
}
