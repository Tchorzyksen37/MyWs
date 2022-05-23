package pl.tchorzyksen.my.web.service.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import pl.tchorzyksen.my.web.service.AbstractFunctionalSpec
import pl.tchorzyksen.my.web.service.model.request.PersonRequest
import pl.tchorzyksen.my.web.service.model.request.UserRequest
import pl.tchorzyksen.my.web.service.model.response.UserResponse

class UserControllerFunctionalSpec extends AbstractFunctionalSpec {

  void "create user"() {
    when:
    ResponseEntity<UserResponse> response = post("/user",
        new UserRequest(person: new PersonRequest(firstName: "John", lastName: "Doe"),
            email: "test@test.com", password: "test123"), UserResponse.class)

    then:
    response.statusCode == HttpStatus.CREATED
  }

}
