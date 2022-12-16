package at.barniverse.backend.barniverse_backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

/**
 * configuration class for manual validation of annotations without using @Valid in controller
 */
@Configuration
public class ValidatorConfiguration {

    @Bean
    public Validator validatorFactory() {
        return new LocalValidatorFactoryBean();
    }

}
