package pl.tchorzyksen.my.web.service.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.tchorzyksen.my.web.service.entities.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

  UserEntity findUserByEmail(String email);

}
