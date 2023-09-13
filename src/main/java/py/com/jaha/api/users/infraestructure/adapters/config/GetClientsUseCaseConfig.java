package py.com.jaha.api.users.infraestructure.adapters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import py.com.jaha.api.users.domain.ports.in.clients.GetClientPort;
import py.com.jaha.api.users.domain.ports.in.clients.GetClientsPageablePort;
import py.com.jaha.api.users.domain.ports.in.clients.GetClientsPort;
import py.com.jaha.api.users.domain.ports.out.ClientsRepositoryPort;
import py.com.jaha.api.users.domain.usecases.clients.GetClientUseCase;
import py.com.jaha.api.users.domain.usecases.clients.GetClientsPageableUseCase;
import py.com.jaha.api.users.domain.usecases.clients.GetClientsUseCase;

@Configuration
public class GetClientsUseCaseConfig {

    @Bean
    public GetClientsPort getClientsUseCase(ClientsRepositoryPort usersRepositoryPort){
        return new GetClientsUseCase(usersRepositoryPort);
    }

    @Bean
    public GetClientPort getClientUseCase(ClientsRepositoryPort usersRepositoryPort){
        return new GetClientUseCase(usersRepositoryPort);
    }

    @Bean
    public GetClientsPageablePort getClientsPageableUseCase(ClientsRepositoryPort usersRepositoryPort){
        return new GetClientsPageableUseCase(usersRepositoryPort);
    }
}
