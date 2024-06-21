package shop.prettydigits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.TimeZone;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class PrettyDigitsServiceApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
		SpringApplication.run(PrettyDigitsServiceApplication.class, args);
	}

}
