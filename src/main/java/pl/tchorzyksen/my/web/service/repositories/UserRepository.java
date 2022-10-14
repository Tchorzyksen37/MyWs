package pl.tchorzyksen.my.web.service.repositories;

import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.tchorzyksen.my.web.service.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
  UserEntity findUserByEmail(String email);

  @Query("FROM users")
  Set<UserEntity> findAll();

}
