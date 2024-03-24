package iut.info3.betterstravaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Startup class of the application.
 */
@SpringBootApplication
public class BetterStravaApiApplication {

    /**
     * Default constructor.
     */
    protected BetterStravaApiApplication() {
    }

    /**
     * Main method to start the application.
     * @param args arguments of the application (not used)
     */
    public static void main(final String[] args) {
        SpringApplication.run(BetterStravaApiApplication.class, args);
    }


}
