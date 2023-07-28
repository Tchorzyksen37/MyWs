package pl.tchorzyksen.my.service.backend.controller

import org.springframework.http.HttpStatus
import pl.tchorzyksen.my.service.backend.AbstractIntegrationSpec
import pl.tchorzyksen.my.service.backend.orm.GreetingEntity

class GreetingControllerIntegrationSpec extends AbstractIntegrationSpec {

  void "get #requestParam greeting #result "(String requestParam, String result) {
    given: "uri"
    String uri = requestParam == null ? "/greeting" : "/greeting?name=${requestParam}"

    when:
    def response = get(uri, GreetingEntity.class)

    then:
    response.statusCode == HttpStatus.OK
    response.body.content() == result

    where:
    requestParam || result
    null         || "Hello, World!"
    "test"       || "Hello, test!"

  }

}
