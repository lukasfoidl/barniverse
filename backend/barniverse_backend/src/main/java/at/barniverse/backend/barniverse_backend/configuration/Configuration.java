package at.barniverse.backend.barniverse_backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

// configuration for manual validation (without @Valid)
@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public Validator validatorFactory() {
        return new LocalValidatorFactoryBean();
    }

}
