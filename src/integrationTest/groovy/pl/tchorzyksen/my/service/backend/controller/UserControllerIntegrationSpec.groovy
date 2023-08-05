package pl.tchorzyksen.my.service.backend.controller

import org.flywaydb.core.Flyway
import org.flywaydb.core.api.configuration.FluentConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import pl.tchorzyksen.my.service.backend.AbstractIntegrationSpec
import pl.tchorzyksen.my.service.backend.exception.ResourceNotFoundException
import pl.tchorzyksen.my.service.backend.exception.model.ExceptionResponse
import pl.tchorzyksen.my.service.backend.model.request.PersonRequest
import pl.tchorzyksen.my.service.backend.model.request.UserRequest
import pl.tchorzyksen.my.service.backend.model.response.UserPageableResponse
import pl.tchorzyksen.my.service.backend.model.response.UserResponse
import pl.tchorzyksen.my.service.backend.repositories.UserRepository
import pl.tchorzyksen.my.service.backend.service.UserService

import javax.sql.DataSource
import java.time.Clock
import java.time.LocalDateTime

@Import(FlywayConfigUsers.class)
class UserControllerIntegrationSpec extends AbstractIntegrationSpec {

  @Autowired
  private Clock testClock

  @Autowired
  private Flyway flyway

  @Autowired
  private UserRepository userRepository

  def setup() {
    flyway.clean()
    flyway.migrate()
  }

  void "Should return pageable user response"() {
    given:
    def userResponse = Set.of(
            new UserResponse(id: 10, version: 0, createdDateTime: LocalDateTime.now(testClock),
                    lastModifiedDateTime: LocalDateTime.now(testClock), userId: "userId0", email: "email0@domain.com"),
            new UserResponse(id: 11, version: 0, createdDateTime: LocalDateTime.now(testClock),
                    lastModifiedDateTime: LocalDateTime.now(testClock), userId: "userId1", email: "email1@domain.com"))

    when:
    def response = get("/user?size=5&page=0", UserPageableResponse.class)

    then:
    response.statusCode == HttpStatus.OK
    response.getBody().page == 0
    response.getBody().size == 5
    response.getBody().totalPages == 1
    response.getBody().result.containsAll(userResponse)

  }

  void "Fetch non existing user should response with 404 not found with appropriate reason"() {
    given:
    Integer nonExistingUserId = 720

    when:
    ResponseEntity<ExceptionResponse> response = get("/user/${nonExistingUserId}", ExceptionResponse.class)

    then:
    response.statusCode == HttpStatus.NOT_FOUND
    response.getBody().reason() == String.format(ResourceNotFoundException.MESSAGE_FORMAT, UserService.RESOURCE_NAME, nonExistingUserId)
  }

  void "Create user with already existing email address should response with 400 bad request with appropriate reason"() {
    given:
    String alreadyExistingEmailAddress = "email0@domain.com"
    when:
    def response
    response = post "/user",
            new UserRequest(email: alreadyExistingEmailAddress, password: "test123",
                    person: new PersonRequest(firstName: "John", lastName: "Doe")), ExceptionResponse.class

    then:
    response.statusCode == HttpStatus.BAD_REQUEST
    response.getBody().reason() == String.format(UserService.NEW_USER_CREATION_FAILED_EMAIL_ALREADY_EXIST, alreadyExistingEmailAddress)
  }

  void "Create user happy path"() {
    when:
    ResponseEntity<UserResponse> response = post("/user",
            new UserRequest(person: new PersonRequest(firstName: "John", lastName: "Doe"),
                    email: "test@test.com", password: "test123"), UserResponse.class)

    then:
    response.statusCode == HttpStatus.CREATED
  }

  @TestConfiguration
  private static class FlywayConfigUsers {

    @Autowired
    private DataSource dataSource

    @Bean
    Flyway flyway() {
      var configuration = new FluentConfiguration()
              .cleanDisabled(false)
              .dataSource(dataSource)
              .locations("db/migration", "test-data/users")

      return new Flyway(configuration)
    }

  }
}
