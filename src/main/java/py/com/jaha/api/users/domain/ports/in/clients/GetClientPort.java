package py.com.jaha.api.users.domain.ports.in.clients;

import py.com.jaha.api.users.domain.commands.clients.GetClientResponse;
import py.com.jaha.api.users.domain.commands.clients.GetClientsCommand;
import py.com.jaha.api.users.domain.usecases.UseCase;

public interface GetClientPort extends UseCase<GetClientResponse, GetClientsCommand> {
}
