package pl.tchorzyksen.tchorzyksen.ui.model.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import pl.tchorzyksen.AbstractFunctionalSpec
import pl.tchorzyksen.ui.model.request.PersonRequestModel
import pl.tchorzyksen.ui.model.request.UserRequestModel
import pl.tchorzyksen.ui.model.response.UserResponse

class UserControllerFunctionalSpec extends AbstractFunctionalSpec {

  void "create user"() {
    when:
    ResponseEntity<UserResponse> response = post("/user",
        new UserRequestModel(person: new PersonRequestModel(firstName: "John", lastName: "Doe"),
            email: "test@test.com", password: "test123"), UserResponse.class)

    then:
    response.statusCode == HttpStatus.CREATED
  }

}
