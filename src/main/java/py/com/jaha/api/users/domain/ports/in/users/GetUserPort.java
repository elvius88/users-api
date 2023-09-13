package py.com.jaha.api.users.domain.ports.in.users;

import py.com.jaha.api.users.domain.commands.users.GetUserResponse;
import py.com.jaha.api.users.domain.commands.users.GetUsersCommand;
import py.com.jaha.api.users.domain.usecases.UseCase;

public interface GetUserPort extends UseCase<GetUserResponse, GetUsersCommand> {
}
