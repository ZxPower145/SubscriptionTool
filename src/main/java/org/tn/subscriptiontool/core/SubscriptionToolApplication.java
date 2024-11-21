package org.tn.subscriptiontool.core;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.tn.subscriptiontool.core.security.models.Role;
import org.tn.subscriptiontool.core.security.repository.RoleRepository;

import java.time.LocalDateTime;


@SpringBootApplication
@EnableJpaAuditing
public class SubscriptionToolApplication {

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("USER").isEmpty()) {
                roleRepository.save(
                        Role.builder()
                                .name("USER")
                                .createdDate(LocalDateTime.now())
                                .build()
                );
            }
            if (roleRepository.findByName("ADMIN").isEmpty()) {
                roleRepository.save(
                        Role.builder()
                                .name("ADMIN")
                                .createdDate(LocalDateTime.now())
                                .build()
                );
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SubscriptionToolApplication.class, args);
    }
}
