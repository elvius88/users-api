package py.com.jaha.api.users.domain.ports.out;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.jaha.api.users.domain.models.users.User;

public interface UsersRepositoryPort {

  User getUserByBranchId(String id);

  List<User> getUsersBy(String name, String categoryId, String establishmentId);

  Page<User> getPageableUsersBy(String latitude, String longitude, Pageable pageable);
}
