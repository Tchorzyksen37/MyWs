package pl.tchorzyksen.my.web.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.tchorzyksen.my.web.service.security.AppProperties;

@SpringBootApplication
public class MyWebService {

  public static void main(String[] args) {
    SpringApplication.run(MyWebService.class, args);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SpringApplicationContext springApplicationContext() {
    return new SpringApplicationContext();
  }

  @Bean(name = "AppProperties")
  public AppProperties getAppProperties() {
    return new AppProperties();
  }
}
