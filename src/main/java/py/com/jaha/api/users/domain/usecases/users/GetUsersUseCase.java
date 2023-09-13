package py.com.jaha.api.users.domain.usecases.users;

import static py.com.jaha.api.users.domain.models.commons.enums.ErrorCatalog.NOT_FOUND;
import static py.com.jaha.api.users.domain.usecases.utils.LogUtil.logAndThrows;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import py.com.jaha.api.users.domain.commands.users.GetUsersCommand;
import py.com.jaha.api.users.domain.commands.users.GetUsersResponse;
import py.com.jaha.api.users.domain.ports.in.users.GetUsersPort;
import py.com.jaha.api.users.domain.ports.out.UsersRepositoryPort;
import py.com.jaha.api.users.domain.usecases.users.mappers.GetUserResponseMapper;

@Slf4j
@RequiredArgsConstructor
public class GetUsersUseCase implements GetUsersPort {

  private final UsersRepositoryPort usersRepositoryPort;

  @Override
  public GetUsersResponse execute(GetUsersCommand command) {
    return Try.of(() -> usersRepositoryPort.getUsersBy(command.getName(), command.getCategoryId(), command.getEstablishmentId()))
        .filter(establishments -> !CollectionUtils.isEmpty(establishments))
        .map(GetUserResponseMapper.INSTANCE::toGetUserResponseList)
        .map(response -> GetUsersResponse.builder().users(response).build())
        .onSuccess(response -> log.debug("Query has been successful: [{}]", response))
        .onFailure(logAndThrows(log, "Error querying users data by criteria: [{}]", NOT_FOUND))
        .get();
  }
}
