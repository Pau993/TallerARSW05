package edu.eci.arsw.blueprints.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import edu.eci.arsw.blueprints.filters.BlueprintFilter;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.persistence.impl.BluePrintFilterInterface;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;

@Primary
@Configuration
@ComponentScan(basePackages = "edu.eci.arsw.blueprints")
public class AppConfig {

    @Bean
    public BlueprintsPersistence blueprintsPersistence() {
        return new InMemoryBlueprintPersistence();
    }

    @Bean
    public BlueprintFilter blueprintFilter() {
        return new BluePrintFilterInterface();
    }
}