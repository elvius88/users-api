package py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users.mapper;

import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import py.com.jaha.api.users.domain.models.users.User;
import py.com.jaha.api.users.infraestructure.adapters.out.commons.IMapper;
import py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users.entities.Users;

@Mapper
public interface UsersMapper extends IMapper<User, Users> {

  UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

  default Page<User> toUserPageableResponse(Page<Users> clientsPage) {
    return new PageImpl<>(clientsPage.stream()
        .map(UsersMapper.INSTANCE::toDomain)
        .collect(Collectors.toList()),
        clientsPage.getPageable(),
        clientsPage.getTotalElements());
  }
}
