package riot.lcgs.riotlcgsbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import riot.lcgs.riotlcgsbe.web.dto.object.Metrics;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication(scanBasePackages = {"riot.lcgs.riotlcgsbe.jpa.repository"} ,exclude={ JpaRepositoriesAutoConfiguration.class, DataSourceAutoConfiguration.class }) // DB 연결 확인 무시
//@SpringBootApplication
public class RiotLcgsBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiotLcgsBeApplication.class, args);

		Metrics[] metrics = new Metrics[10];

		metrics[0] = new Metrics("Hello1", 1, 6, 8, 5804, 34, 6696, 49, 2, 0,
				0, 19, 0, true, false, true, false, false, false);
		metrics[1] = new Metrics("Hello2", 4, 2, 6, 8757, 194, 19733, 17, 2, 0,
				0, 19, 0, false, false, true, false, false, false);
		metrics[2] = new Metrics("Hello3", 1, 9, 5, 5791, 96, 14925, 11, 2, 0,
				0, 19, 0, false, false, true, false, false, false);
		metrics[3] = new Metrics("Hello4", 4, 8, 7, 7374, 116, 9968, 11, 2, 0,
				0, 19, 0, false, false, true, false, false, false);
		metrics[4] = new Metrics("Hello5", 9, 6, 5, 10301, 164, 20031, 14, 2, 2,
				0, 19, 0, false, false, true, false, false, false);
		metrics[5] = new Metrics("Hello6", 11, 2, 7, 10510, 158, 25489, 24, 11, 7,
				10, 31, 0, false, false, false, false, false, true);
		metrics[6] = new Metrics("Hello7", 1, 7, 12, 6142, 19, 5892, 60, 11, 0,
				5, 31, 0, false, false, false, false, false, true);
		metrics[7] = new Metrics("Hello8", 7, 3, 11, 10724, 139, 15398, 19, 11, 2,
				0, 31, 0, false, false, false, false, false, true);
		metrics[8] = new Metrics("Hello9", 5, 3, 9, 9729, 166, 12455, 23, 11, 0,
				10, 31, 0, false, false, false, false, false, true);
		metrics[9] = new Metrics("Hello10", 7, 4, 8, 11674, 206, 16430, 19, 11, 2,
				5, 31, 0, false, true, false, false, false, true);

		List<Metrics> list = Arrays.asList(metrics);

		Arrays.sort(metrics, (m1, m2) -> Integer.compare(m1.getKill(),m2.getKill()));

		for (Metrics m : metrics) {
			System.out.println(m.toString());
		}
	}

}
