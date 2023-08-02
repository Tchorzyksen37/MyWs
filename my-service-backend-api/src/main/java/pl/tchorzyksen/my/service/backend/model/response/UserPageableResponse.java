package pl.tchorzyksen.my.service.backend.model.response;

import java.util.Set;

public class UserPageableResponse extends AbstractPageableResponse<UserResponse> {

  public UserPageableResponse() {

  }

  public UserPageableResponse(int currentPage, int numberOfPages, int pageSize, Set<UserResponse> result) {
    super(currentPage, numberOfPages, pageSize, result);
  }

}
