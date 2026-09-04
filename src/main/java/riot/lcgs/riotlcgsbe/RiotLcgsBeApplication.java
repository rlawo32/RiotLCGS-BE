package riot.lcgs.riotlcgsbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication(scanBasePackages = {"riot.lcgs.riotlcgsbe.jpa.repository"} ,exclude={ JpaRepositoriesAutoConfiguration.class, DataSourceAutoConfiguration.class }) // DB 연결 확인 무시
@SpringBootApplication
@EnableScheduling
public class RiotLcgsBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiotLcgsBeApplication.class, args);
	}

}
