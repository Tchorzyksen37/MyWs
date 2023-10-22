package pl.tchorzyksen.my.service.backend.orm.post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = PostCommentDetails.POST_COMMENT_DETAILS_TABLE_NAME)
public class PostCommentDetails {

  public static final String POST_COMMENT_DETAILS_TABLE_NAME = "post_comment_details";

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

}
