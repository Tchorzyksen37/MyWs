package pl.tchorzyksen.my.web.service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.tchorzyksen.my.web.service.entities.UserModifiableEntity;

@Repository
public interface UserRepository extends CrudRepository<UserModifiableEntity, Long> {
  UserModifiableEntity findUserByEmail(String email);
}
