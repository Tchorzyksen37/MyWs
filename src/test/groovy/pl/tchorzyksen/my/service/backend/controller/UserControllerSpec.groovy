package pl.tchorzyksen.my.service.backend.controller

import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import pl.tchorzyksen.my.service.backend.configuration.ModelMapperConfiguration
import pl.tchorzyksen.my.service.backend.model.dto.PersonDto
import pl.tchorzyksen.my.service.backend.model.dto.UserDto
import pl.tchorzyksen.my.service.backend.model.request.PersonRequest
import pl.tchorzyksen.my.service.backend.model.request.UserRequest
import pl.tchorzyksen.my.service.backend.model.response.UserResponse

import pl.tchorzyksen.my.service.backend.service.UserService

import spock.lang.Specification

class UserControllerSpec extends Specification {

  ModelMapper modelMapper = new ModelMapperConfiguration().modelMapper()

  UserService userService = Mock(UserService.class)

  UserController userController = new UserController(userService, modelMapper)

  def "Request is correctly mapped and passed to service"() {
    given: "User request model"
    UserRequest model = new UserRequest(email: "test@domain.com",
            person: new PersonRequest(firstName: "firstName", lastName: "lastName"), password: "test")

    when:
    ResponseEntity<UserResponse> response = userController.createUser(model)

    then:
    1 * userService.createUser({
      verifyAll(it, UserDto)
              {
                assert it.email == model.email
                assert it.person.firstName == model.person.firstName
                assert it.person.lastName == model.person.lastName
                assert it.password == model.password
              }
    }) >> new UserDto(email: "test@domain.com", person: new PersonDto(firstName: "firstName", lastName: "lastName"))

    response.statusCode == HttpStatus.CREATED
    response.body.email == model.email
    response.body.person.firstName == model.person.firstName
    response.body.person.lastName == model.person.lastName

  }

}
