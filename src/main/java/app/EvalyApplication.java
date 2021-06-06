package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableAspectJAutoProxy
@ComponentScan({ "controller", "service", "config", "principal", "aop", "mail", "service.impl", "dto", "repository", "utils", "validator"} )
@EntityScan("model")
@EnableJpaRepositories("repository")
public class EvalyApplication  {

	public static void main(String[] args) {
		SpringApplication.run(EvalyApplication.class, args);
	}

}
