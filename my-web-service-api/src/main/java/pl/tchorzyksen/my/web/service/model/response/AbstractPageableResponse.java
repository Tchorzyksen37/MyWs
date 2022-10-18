package pl.tchorzyksen.my.web.service.model.response;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
abstract class AbstractPageableResponse<T> {

  private int page;

  private int totalPages;

  private int size;

  private Set<T> result;

}

