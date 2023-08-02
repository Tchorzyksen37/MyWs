package pl.tchorzyksen.my.service.backend.model.response;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
abstract class AbstractPageableResponse<T> {

  private int page;

  private int totalPages;

  private int size;

  private Set<T> result;

}

