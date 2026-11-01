package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"application", "controller", "service", "repository", "model"})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "model")
public class TrabalhoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrabalhoApplication.class, args);
    }
}