package tchorzyksen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tchorzyksen.security.AppProperties;

@SpringBootApplication
public class MyWs {
    public static void main(String[] args) {
        SpringApplication.run(MyWs.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    @Bean(name="AppProperties")
    public AppProperties getAppProperties(){
        return new AppProperties();
    }

}
