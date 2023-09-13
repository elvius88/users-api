package py.com.jaha.api.users.infraestructure.adapters.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import py.com.jaha.api.users.domain.commands.clients.GetClientsCommand;

@Mapper
public interface ClientsCommandMapper {

  ClientsCommandMapper INSTANCE = Mappers.getMapper(ClientsCommandMapper.class);

  default GetClientsCommand toCommand(String id,
                                      String name,
                                      String lastname,
                                      String email,
                                      String city,
                                      Pageable pageable) {
    return GetClientsCommand.builder()
        .id(id).name(name).lastname(lastname).email(email).city(city)
        .pageable(pageable).build();
  }
}
