package py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users.mapper;

import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import py.com.jaha.api.users.domain.models.users.Client;
import py.com.jaha.api.users.infraestructure.adapters.out.commons.IMapper;
import py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users.entities.Clients;

@Mapper
public interface ClientsMapper extends IMapper<Client, Clients> {

  ClientsMapper INSTANCE = Mappers.getMapper(ClientsMapper.class);

  default Page<Client> toClientPageableResponse(Page<Clients> clientsPage) {
    return new PageImpl<>(clientsPage.stream()
        .map(ClientsMapper.INSTANCE::toDomain)
        .collect(Collectors.toList()),
        clientsPage.getPageable(),
        clientsPage.getTotalElements());
  }
}
