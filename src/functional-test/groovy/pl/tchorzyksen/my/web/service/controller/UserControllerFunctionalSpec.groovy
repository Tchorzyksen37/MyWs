package pl.tchorzyksen.my.web.service.controller

import java.time.LocalDateTime
import javax.sql.DataSource
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.configuration.FluentConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import pl.tchorzyksen.my.web.service.AbstractFunctionalSpec
import pl.tchorzyksen.my.web.service.model.request.PersonRequest
import pl.tchorzyksen.my.web.service.model.request.UserRequest
import pl.tchorzyksen.my.web.service.model.response.UserPageableResponse
import pl.tchorzyksen.my.web.service.model.response.UserResponse
import pl.tchorzyksen.my.web.service.repositories.UserRepository
import spock.lang.Shared

@Import(FlywayConfigUsers.class)
class UserControllerFunctionalSpec extends AbstractFunctionalSpec {

  @Shared
  private static final Set<UserResponse> users = new HashSet<>()

  @Autowired
  private Flyway flyway;

  @Autowired
  private UserRepository userRepository

  def setup() {
    flyway.clean()
    flyway.migrate()
  }

  def setupSpec() {
    users.add(new UserResponse(id: 0, version: 0, createdDateTime: LocalDateTime.parse("2022-10-14T17:59:57.165952"),
        lastModifiedDateTime: LocalDateTime.parse("2022-10-14T17:59:57.165952"), userId: "userId0", email: "email0@domain.com"))

    users.add(new UserResponse(id: 1, version: 0, createdDateTime: LocalDateTime.parse("2022-10-14T17:59:57.165952"),
        lastModifiedDateTime: LocalDateTime.parse("2022-10-14T17:59:57.165952"), userId: "userId1", email: "email1@domain.com"))
  }

  void "Should return pageable user response"() {
    when:
    ResponseEntity<UserPageableResponse> response = get("/user/?size=5&page=0", UserPageableResponse.class)
    def userInDb = userRepository.findAll().toList()

    then:
    response.statusCode == HttpStatus.OK
    response.getBody().page == 0
    response.getBody().size == 5
    response.getBody().totalPages == 1
    response.getBody().result.containsAll(users)

  }

  void "create user"() {
    when:
    ResponseEntity<UserResponse> response = post("/user",
        new UserRequest(person: new PersonRequest(firstName: "John", lastName: "Doe"),
            email: "test@test.com", password: "test123"), UserResponse.class)

    then:
    response.statusCode == HttpStatus.CREATED
  }

  @TestConfiguration
  public static class FlywayConfigUsers {

    @Autowired
    private DataSource dataSource;

    @Bean
    Flyway flyway() {
      var configuration = new FluentConfiguration()
          .dataSource(dataSource)
          .locations("db/migration", "test-data/users")

      return new Flyway(configuration)
    }

  }
}
