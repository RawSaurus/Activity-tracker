package com.miroslav.acitivity_tracker;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.miroslav.acitivity_tracker"})
@EnableJpaAuditing
public class RepositoryTestConfig {
}
