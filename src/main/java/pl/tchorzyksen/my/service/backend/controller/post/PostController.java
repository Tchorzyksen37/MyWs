package pl.tchorzyksen.my.service.backend.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tchorzyksen.my.service.backend.orm.post.Post;
import pl.tchorzyksen.my.service.backend.orm.post.PostComment;
import pl.tchorzyksen.my.service.backend.repositories.PostRepository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Transactional
class PostController {

  private final PostRepository postRepository;

  @GetMapping("/{postId}")
  public ResponseEntity<PostResponse> getOne(@PathVariable UUID postId) {
    var inDb = postRepository.findById(postId).orElse(null);
    return ResponseEntity.ok(PostResponse.mapToResponse(inDb));
  }

  @PostMapping
  public ResponseEntity<PostResponse> create(@RequestBody Post post) {
    post.getPostComments().forEach(postComment -> postComment.setPost(post));
    var inDb = postRepository.save(post);
    return ResponseEntity.status(HttpStatus.CREATED).body(PostResponse.mapToResponse(inDb));
  }

  @PutMapping
  public ResponseEntity<PostResponse> update(@RequestBody Post post) {
    var inDb = postRepository.findById(post.getId()).orElse(null);
    if (inDb != null) {
      inDb.getPostComments().forEach(postComment -> postComment.setPost(null));
      inDb.getPostComments().clear();
      inDb.getPostComments().addAll(post.getPostComments());
      post.getPostComments().forEach(postComment -> postComment.setPost(inDb));
      return ResponseEntity.ok(PostResponse.mapToResponse(postRepository.save(inDb)));
    }
    return ResponseEntity.ok(PostResponse.mapToResponse(null));
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> delete(@PathVariable UUID postId) {
    postRepository.deleteById(postId);
    return ResponseEntity.noContent().build();
  }

  public record PostResponse(UUID id, long version, Set<UUID> postCommentsIds) {
    public static PostResponse mapToResponse(Post post) {
      if (post == null) {
        return null;
      }
      return new PostResponse(post.getId(), post.getVersion(), getPostCommentsIds(post));
    }

    private static Set<UUID> getPostCommentsIds(Post post) {
      return post.getPostComments()
              .stream()
              .map(PostComment::getId)
              .collect(Collectors.toSet());
    }

  }

}
