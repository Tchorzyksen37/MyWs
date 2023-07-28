package pl.tchorzyksen.my.service.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.tchorzyksen.my.service.backend.orm.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>, CrudRepository<UserEntity, Long> {

  UserEntity findUserByEmail(String email);

}
