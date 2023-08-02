package pl.tchorzyksen.my.service.backend

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import pl.tchorzyksen.my.service.backend.configuration.IntegrationTestConfiguration
import pl.tchorzyksen.my.service.backend.configuration.TestClockConfiguration
import pl.tchorzyksen.my.service.backend.security.SecurityConstants
import spock.lang.Specification

@Testcontainers
@ActiveProfiles("integration-test")
@SpringBootTest(classes = [IntegrationTestConfiguration.class, TestClockConfiguration.class],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractIntegrationSpec extends Specification {

  @Autowired
  protected IntegrationTestConfiguration integrationTestConfiguration

  @Autowired
  protected ModelMapper modelMapper

  @Autowired
  private TestRestTemplate testRestTemplate

  static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer(DockerImageName.parse("postgres:15.3"))

  static {
    postgreSQLContainer.start()
  }

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
  }

  protected <T, R> ResponseEntity<T> post(String uri, R request, Class<T> responseClass) {
    String stringUri = UriComponentsBuilder.fromUriString(uri).build().toUriString()
    return testRestTemplate.postForEntity(stringUri, request, responseClass, [:])
  }

  protected <T> ResponseEntity<T> get(String uri, Class<T> responseClass) {
    String token = "Bearer " + getToken()
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>()
    headers.add("Authorization", token)
    String stringUri = UriComponentsBuilder.fromUriString(uri).build().toUriString()

    return testRestTemplate.exchange(stringUri, HttpMethod.GET, new HttpEntity<Object>(null, headers), responseClass, [:])
  }

  private String getToken() {
    return Jwts.builder()
            .setSubject("admin")
            .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, integrationTestConfiguration.getTestTokenSecret())
            .compact()
  }

}
