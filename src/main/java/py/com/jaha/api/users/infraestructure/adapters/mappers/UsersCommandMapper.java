package py.com.jaha.api.users.infraestructure.adapters.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import py.com.jaha.api.users.domain.commands.users.GetUsersCommand;

@Mapper
public interface UsersCommandMapper {

  UsersCommandMapper INSTANCE = Mappers.getMapper(UsersCommandMapper.class);

  default GetUsersCommand toCommand(String id,
                                    String name,
                                    String clientId,
                                    String establishmentId,
                                    String latitude,
                                    String longitude,
                                    Pageable pageable) {
    return GetUsersCommand.builder()
        .id(id).name(name)
        .categoryId(clientId).establishmentId(establishmentId)
        .latitude(latitude).longitude(longitude).pageable(pageable).build();
  }
}
