package py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users;

import io.vavr.control.Try;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import py.com.jaha.api.users.domain.models.users.User;
import py.com.jaha.api.users.domain.ports.out.UsersRepositoryPort;
import py.com.jaha.api.users.infraestructure.adapters.out.sqlserver.users.mapper.UsersMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersRepositoryAdapter implements UsersRepositoryPort {

  private final UsersRepository usersRepository;

  @Override
  public User getUserByBranchId(String id) {
    return Try.of(() -> usersRepository.findById(id))
        .map(Optional::get)
        .map(UsersMapper.INSTANCE::toDomain)
        .get();
  }

  @Override
  public List<User> getUsersBy(String name, String categoryId, String establishmentId) {
    return Try.of(() -> usersRepository.findUsersBy(name))
        .map(UsersMapper.INSTANCE::toDomainList)
        .get();
  }

  @Override
  public Page<User> getPageableUsersBy(String latitude, String longitude, Pageable pageable) {
    return Try.of(() -> usersRepository.findPageableUsersBy(latitude, pageable))
        .map(UsersMapper.INSTANCE::toUserPageableResponse)
        .get();
  }
}
