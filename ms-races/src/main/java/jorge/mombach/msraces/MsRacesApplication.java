package jorge.mombach.msraces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsRacesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRacesApplication.class, args);
	}

}
