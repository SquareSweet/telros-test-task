package prj.sqsw.telrostest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class TelrosTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelrosTestApplication.class, args);
	}

}
