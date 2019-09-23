package josh0766.oauth2jwtexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableAspectJAutoProxy
@SpringBootApplication
public class Oauth2JwtExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2JwtExampleApplication.class, args);
    }

}
