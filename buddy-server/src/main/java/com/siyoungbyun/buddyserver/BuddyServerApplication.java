package com.siyoungbyun.buddyserver;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BuddyServerApplication {

    public static void main(String[] args) {

        // Get active profile (default to 'development' if not set)
        String profile = System.getProperty("spring.profiles.active", "development");

        // Load the corresponding .env file
        Dotenv dotenv = Dotenv.configure()
                .filename(".env." + profile) // Load .env.development, .env.production, etc.
                .ignoreIfMissing() // Avoid errors if file is missing
                .load();

        // Set environment variables
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(BuddyServerApplication.class, args);
    }

}
