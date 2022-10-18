package pl.tchorzyksen.my.web.service.controller


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import pl.tchorzyksen.my.web.service.AbstractFunctionalSpec
import pl.tchorzyksen.my.web.service.entities.UserEntity
import pl.tchorzyksen.my.web.service.model.request.PersonRequest
import pl.tchorzyksen.my.web.service.model.request.UserRequest
import pl.tchorzyksen.my.web.service.model.response.UserPageableResponse
import pl.tchorzyksen.my.web.service.model.response.UserResponse
import pl.tchorzyksen.my.web.service.repositories.UserRepository

class UserControllerFunctionalSpec extends AbstractFunctionalSpec {

  private final Set<UserEntity> users = new HashSet<>()

  @Autowired
  private UserRepository userRepository

  def setup() {
    users.add(userRepository.save(new UserEntity(userId: "userId1", email: "email1@domain.com", emailVerificationStatus: false,
        isActive: false, encryptedPassword: 'encrypted_password')))
    users.add(userRepository.save(new UserEntity(userId: "userId2", email: "email2@domain.com", emailVerificationStatus: false,
        isActive: false, encryptedPassword: 'encrypted_password')))
  }

  void "Should return pageable user response"() {
    when:
    ResponseEntity<UserPageableResponse> response = get("/user/?size=3&page=0", UserPageableResponse.class)

    then:
    response.statusCode == HttpStatus.OK
    response.getBody().page == 0
    response.getBody().size == 3
    response.getBody().totalPages == 1
    response.getBody().result.containsAll(users.stream().map(user -> mapper.map(user, UserResponse.class)).toList())

  }

  void "create user"() {
    when:
    ResponseEntity<UserResponse> response = post("/user",
        new UserRequest(person: new PersonRequest(firstName: "John", lastName: "Doe"),
            email: "test@test.com", password: "test123"), UserResponse.class)

    then:
    response.statusCode == HttpStatus.CREATED
  }

}
