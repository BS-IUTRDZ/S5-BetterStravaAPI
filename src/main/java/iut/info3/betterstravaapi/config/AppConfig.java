package iut.info3.betterstravaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

/**
 * Configuration de l'application.
 */

@Configuration
public class AppConfig {

    /**
     * Constructeur par defaut.
     */
    protected AppConfig() { };

    /**
     * Recuperation de variables d'environements pour les fichiers du main.
     * @return la configuration.
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
