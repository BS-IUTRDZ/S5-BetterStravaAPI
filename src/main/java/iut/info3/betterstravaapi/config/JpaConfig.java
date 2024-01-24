package iut.info3.betterstravaapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "iut.info3.betterstravaapi.user")
public class JpaConfig {
}
