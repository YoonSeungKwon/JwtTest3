package yoon.test.jwtTest3;

import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class JwtTest3Application {

	public static void main(String[] args) {
		SpringApplication.run(JwtTest3Application.class, args);
	}

}
