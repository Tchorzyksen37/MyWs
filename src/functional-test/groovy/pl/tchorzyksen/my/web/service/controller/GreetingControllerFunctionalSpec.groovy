package pl.tchorzyksen.my.web.service.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import pl.tchorzyksen.my.web.service.AbstractFunctionalSpec
import pl.tchorzyksen.my.web.service.orm.GreetingEntity

class GreetingControllerFunctionalSpec extends AbstractFunctionalSpec {

  void "get #requestParam greeting #result "(String requestParam, String result) {
    given: "uri"
    String uri = requestParam == null ? "/greeting" : "/greeting?name=${requestParam}"

    when:
    ResponseEntity<GreetingEntity> response = get(uri, GreetingEntity.class)

    then:
    response.statusCode == HttpStatus.OK
    response.body.content() == result

    where:
    requestParam || result
    null         || "Hello, World!"
    "test"       || "Hello, test!"

  }

}
