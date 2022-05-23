package pl.tchorzyksen.my.web.service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.tchorzyksen.my.web.service.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
  UserEntity findUserByEmail(String email);
}
