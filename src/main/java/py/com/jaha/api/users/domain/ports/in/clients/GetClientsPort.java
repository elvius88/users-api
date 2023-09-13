package py.com.jaha.api.users.domain.ports.in.clients;

import py.com.jaha.api.users.domain.commands.clients.GetClientsCommand;
import py.com.jaha.api.users.domain.commands.clients.GetClientsResponse;
import py.com.jaha.api.users.domain.usecases.UseCase;

public interface GetClientsPort extends UseCase<GetClientsResponse, GetClientsCommand> {
}
