package py.com.jaha.api.users.infraestructure.adapters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import py.com.jaha.api.users.domain.ports.in.users.GetUserPort;
import py.com.jaha.api.users.domain.ports.in.users.GetUsersPageablePort;
import py.com.jaha.api.users.domain.ports.in.users.GetUsersPort;
import py.com.jaha.api.users.domain.ports.out.UsersRepositoryPort;
import py.com.jaha.api.users.domain.usecases.users.GetUserUseCase;
import py.com.jaha.api.users.domain.usecases.users.GetUsersPageableUseCase;
import py.com.jaha.api.users.domain.usecases.users.GetUsersUseCase;

@Configuration
public class GetUsersUseCaseConfig {

    @Bean
    public GetUsersPort getUsersUseCase(UsersRepositoryPort usersRepositoryPort){
        return new GetUsersUseCase(usersRepositoryPort);
    }

    @Bean
    public GetUserPort getUserUseCase(UsersRepositoryPort usersRepositoryPort){
        return new GetUserUseCase(usersRepositoryPort);
    }

    @Bean
    public GetUsersPageablePort getUsersPageableUseCase(UsersRepositoryPort usersRepositoryPort){
        return new GetUsersPageableUseCase(usersRepositoryPort);
    }
}
