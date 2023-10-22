package pl.tchorzyksen.my.service.backend.orm.post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode
@Table(name = PostComment.POST_COMMENT_TABLE_NAME)
public class PostComment {

  public static final String POST_COMMENT_TABLE_NAME = "post_comments";

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String comment;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "post_id")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Post post;

}
