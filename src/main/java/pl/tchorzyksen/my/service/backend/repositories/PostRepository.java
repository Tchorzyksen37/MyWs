package pl.tchorzyksen.my.service.backend.repositories;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import pl.tchorzyksen.my.service.backend.orm.post.Post;

import java.util.Optional;
import java.util.UUID;

import static jakarta.persistence.LockModeType.OPTIMISTIC_FORCE_INCREMENT;

public interface PostRepository extends CrudRepository<Post, UUID> {

  @Override
  @Lock(value = OPTIMISTIC_FORCE_INCREMENT)
  Optional<Post> findById(UUID id);

}
