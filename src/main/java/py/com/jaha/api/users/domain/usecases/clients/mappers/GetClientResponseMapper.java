package py.com.jaha.api.users.domain.usecases.clients.mappers;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import py.com.jaha.api.users.domain.commands.clients.GetClientPageableResponse;
import py.com.jaha.api.users.domain.commands.clients.GetClientResponse;
import py.com.jaha.api.users.domain.models.users.Client;

@Mapper
public interface GetClientResponseMapper {

  GetClientResponseMapper INSTANCE = Mappers.getMapper(GetClientResponseMapper.class);

  @Mapping(source = "city.name", target = "city")
  @Mapping(source = "city.state.name", target = "state")
  GetClientPageableResponse toGetClientPageableResponse(Client client);

  @Mapping(source = "city.name", target = "city")
  @Mapping(source = "city.state.name", target = "state")
  @Named(value = "getClientResponse")
  GetClientResponse toGetClientResponse(Client client);

  @IterableMapping(qualifiedByName = "getClientResponse")
  List<GetClientResponse> toGetClientResponseList(List<Client> clients);

  default Page<GetClientPageableResponse> toGetClientsPageableResponse(Page<Client> clientPage) {
    return new PageImpl<>(clientPage.stream()
        .map(GetClientResponseMapper.INSTANCE::toGetClientPageableResponse)
        .collect(Collectors.toList()),
        clientPage.getPageable(),
        clientPage.getTotalElements());
  }
}
