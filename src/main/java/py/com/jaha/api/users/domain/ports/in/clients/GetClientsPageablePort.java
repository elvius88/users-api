package py.com.jaha.api.users.domain.ports.in.clients;

import org.springframework.data.domain.Page;
import py.com.jaha.api.users.domain.commands.clients.GetClientPageableResponse;
import py.com.jaha.api.users.domain.commands.clients.GetClientsCommand;
import py.com.jaha.api.users.domain.usecases.UseCase;

public interface GetClientsPageablePort extends UseCase<Page<GetClientPageableResponse>, GetClientsCommand> {
}
