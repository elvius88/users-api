package py.com.jaha.api.users.domain.ports.in.users;

import org.springframework.data.domain.Page;
import py.com.jaha.api.users.domain.commands.users.GetUserPageableResponse;
import py.com.jaha.api.users.domain.commands.users.GetUsersCommand;
import py.com.jaha.api.users.domain.usecases.UseCase;

public interface GetUsersPageablePort extends UseCase<Page<GetUserPageableResponse>, GetUsersCommand> {
}
