package iut.info3.betterstravaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

/**
 * Configuration of the application.
 */

@Configuration
public class AppConfig {

    /**
     * Default constructor.
     */
    protected AppConfig() { };

    /**
     * Getting environment variables for the main files.
     * @return the configuration
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer =
                new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new FileSystemResource(".env"));
        return configurer;
    }





}
