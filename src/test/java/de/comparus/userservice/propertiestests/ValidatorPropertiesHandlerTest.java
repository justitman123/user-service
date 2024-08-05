package de.comparus.userservice.propertiestests;

import de.comparus.userservice.config.DataSourcesPropertiesHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorPropertiesHandlerTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void emptyDataSourcesTest() {
        DataSourcesPropertiesHandler.DataSourceConfigProperties config = new DataSourcesPropertiesHandler.DataSourceConfigProperties();
        DataSourcesPropertiesHandler.Mapping mapping = new DataSourcesPropertiesHandler.Mapping();

        config.setMapping(mapping); // set empty mapping to check mapping validation

        Set<ConstraintViolation<DataSourcesPropertiesHandler.DataSourceConfigProperties>> violations = validator.validate(config);

        assertThat(violations).isNotEmpty();
        assertEquals("name can't be empty or null", getViolationMessage(violations, "name"));
        assertEquals("strategy can't be empty or null", getViolationMessage(violations, "strategy"));
        assertEquals("url can't be empty or null", getViolationMessage(violations, "url"));
        assertEquals("table can't be empty or null", getViolationMessage(violations, "table"));
        assertEquals("user can't be empty or null", getViolationMessage(violations, "user"));
        assertEquals("password can't be empty or null", getViolationMessage(violations, "password"));

        Set<ConstraintViolation<DataSourcesPropertiesHandler.Mapping>> mappingViolations = validator.validate(mapping);

        assertThat(mappingViolations).isNotEmpty();
        assertEquals("mapping id can't be empty or null", getViolationMessage(mappingViolations, "id"));
        assertEquals("mapping username can't be empty or null", getViolationMessage(mappingViolations, "username"));
        assertEquals("mapping name can't be empty or null", getViolationMessage(mappingViolations, "name"));
        assertEquals("mapping surname can't be empty or null", getViolationMessage(mappingViolations, "surname"));
    }

    private String getViolationMessage(Set<? extends ConstraintViolation<?>> violations, String propertyPath) {
        return violations.stream()
                .filter(violation -> violation.getPropertyPath().toString().equals(propertyPath))
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("");
    }
}
