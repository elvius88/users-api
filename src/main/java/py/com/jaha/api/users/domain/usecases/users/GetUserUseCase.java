package py.com.jaha.api.users.domain.usecases.users;

import static py.com.jaha.api.users.domain.models.commons.enums.ErrorCatalog.NOT_FOUND;
import static py.com.jaha.api.users.domain.usecases.utils.LogUtil.logAndThrows;

import io.vavr.control.Try;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import py.com.jaha.api.users.domain.commands.users.GetUserResponse;
import py.com.jaha.api.users.domain.commands.users.GetUsersCommand;
import py.com.jaha.api.users.domain.ports.in.users.GetUserPort;
import py.com.jaha.api.users.domain.ports.out.UsersRepositoryPort;
import py.com.jaha.api.users.domain.usecases.users.mappers.GetUserResponseMapper;

@Slf4j
@RequiredArgsConstructor
public class GetUserUseCase implements GetUserPort {

  private final UsersRepositoryPort usersRepositoryPort;

  @Override
  public GetUserResponse execute(GetUsersCommand command) {
    return Try.of(() -> usersRepositoryPort.getUserByBranchId(command.getId()))
        .filter(Objects::nonNull)
        .map(GetUserResponseMapper.INSTANCE::toGetUserResponse)
        .onSuccess(response -> log.debug("Query has been successful: [{}]", response))
        .onFailure(logAndThrows(log, "Error querying establishment data by branch: [{}]", NOT_FOUND))
        .get();
  }
}
