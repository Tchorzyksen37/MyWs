package pl.tchorzyksen


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.util.UriComponentsBuilder
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@ActiveProfiles("functional-test")
@SpringBootTest(classes = FunctionalTestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractFunctionalSpec extends Specification {

  @Autowired
  private TestRestTemplate testRestTemplate

  @Shared
  public PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")

  protected <T, R> ResponseEntity<T> post(String uri, R request, Class<T> responseClass){
    String stringUri = UriComponentsBuilder.fromUriString(uri).build().toUriString()
    return testRestTemplate.postForEntity(stringUri, request, responseClass, [:])
  }

  protected <T> ResponseEntity<T> get(String uri, Class<T> responseClass) {
    String stringUri = UriComponentsBuilder.fromUriString(uri).build().toUriString()
    return testRestTemplate.exchange(stringUri, HttpMethod.GET, null, responseClass, [:])
  }


}