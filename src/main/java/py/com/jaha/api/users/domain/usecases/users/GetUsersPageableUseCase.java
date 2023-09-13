package py.com.jaha.api.users.domain.usecases.users;

import static py.com.jaha.api.users.domain.models.commons.enums.ErrorCatalog.NOT_FOUND;
import static py.com.jaha.api.users.domain.usecases.utils.LogUtil.logAndThrows;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import py.com.jaha.api.users.domain.commands.users.GetUserPageableResponse;
import py.com.jaha.api.users.domain.commands.users.GetUsersCommand;
import py.com.jaha.api.users.domain.ports.in.users.GetUsersPageablePort;
import py.com.jaha.api.users.domain.ports.out.UsersRepositoryPort;
import py.com.jaha.api.users.domain.usecases.users.mappers.GetUserResponseMapper;

@Slf4j
@RequiredArgsConstructor
public class GetUsersPageableUseCase implements GetUsersPageablePort {

  private final UsersRepositoryPort usersRepositoryPort;

  @Override
  public Page<GetUserPageableResponse> execute(GetUsersCommand command) {
    return Try.of(() -> usersRepositoryPort.getPageableUsersBy(command.getLatitude(), command.getLongitude(), command.getPageable()))
        .filter(userPage -> !CollectionUtils.isEmpty(userPage.getContent()))
        .map(GetUserResponseMapper.INSTANCE::toGetUsersPageableResponse)
        .onSuccess(response -> log.debug("Query has been successful: [{}]", response))
        .onFailure(logAndThrows(log, "Error querying user data: [{}]", NOT_FOUND))
        .get();
  }

}
