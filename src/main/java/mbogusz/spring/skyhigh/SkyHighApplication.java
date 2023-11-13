package mbogusz.spring.skyhigh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class SkyHighApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkyHighApplication.class, args);
	}

}
