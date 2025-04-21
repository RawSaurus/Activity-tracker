package com.miroslav.acitivity_tracker;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.web.WebAppConfiguration;

@Configuration
//@AutoConfigureMockMvc(addFilters = false)
//@EnableAutoConfiguration
@WebAppConfiguration()
public class ControllerTestConfig {
}
