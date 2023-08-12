package pl.tchorzyksen.my.service.backend.model.response;

import java.time.LocalDateTime;
import java.util.List;

public record LogoDetailsListResponse(List<LogoDetails> logoDetailsList) {

  public record LogoDetails(String logoName, LocalDateTime lastModified){

  }
}
