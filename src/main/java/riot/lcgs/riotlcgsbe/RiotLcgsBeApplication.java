package riot.lcgs.riotlcgsbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class RiotLcgsBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiotLcgsBeApplication.class, args);
	}

}
