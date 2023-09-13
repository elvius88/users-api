package py.com.jaha.api.users.domain.usecases.users.mappers;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import py.com.jaha.api.users.domain.commands.users.GetUserPageableResponse;
import py.com.jaha.api.users.domain.commands.users.GetUserResponse;
import py.com.jaha.api.users.domain.models.users.User;

@Mapper
public interface GetUserResponseMapper {

  GetUserResponseMapper INSTANCE = Mappers.getMapper(GetUserResponseMapper.class);

  GetUserPageableResponse toPageableGetUserResponse(User user);

  @Named(value = "getUserResponse")
  GetUserResponse toGetUserResponse(User user);

  @IterableMapping(qualifiedByName = "getUserResponse")
  List<GetUserResponse> toGetUserResponseList(List<User> users);

  default Page<GetUserPageableResponse> toGetUsersPageableResponse(Page<User> userPage) {
    return new PageImpl<>(userPage.stream()
        .map(GetUserResponseMapper.INSTANCE::toPageableGetUserResponse)
        .collect(Collectors.toList()),
        userPage.getPageable(),
        userPage.getTotalElements());
  }
}
