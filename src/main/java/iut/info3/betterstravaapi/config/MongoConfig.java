package iut.info3.betterstravaapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Class MongoConfig.
 */
@Configuration
@EnableMongoRepositories(basePackages = "iut.info3.betterstravaapi.path")
public class MongoConfig {
}
