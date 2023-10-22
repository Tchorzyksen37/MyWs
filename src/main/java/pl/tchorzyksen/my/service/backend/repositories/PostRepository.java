package pl.tchorzyksen.my.service.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.tchorzyksen.my.service.backend.orm.post.Post;

import java.util.UUID;

public interface PostRepository extends CrudRepository<Post, UUID> {

}
