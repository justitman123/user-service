package de.comparus.userservice.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Configuration
@Validated
@ConfigurationProperties
@PropertySource(value = "classpath:datasource-properties.yml", factory = CustomPropertyLoaderFactory.class)
public class DataSourcesPropertiesHandler {

    private List<DataSourceConfigProperties> dataSources = new ArrayList<>();

    @Getter
    @Setter
    public static class DataSourceConfigProperties {
        @NotBlank(message = "name can't be empty or null")
        private String name;
        @NotBlank(message = "strategy can't be empty or null")
        private String strategy;
        @NotBlank(message = "url can't be empty or null")
        private String url;
        @NotBlank(message = "table can't be empty or null")
        private String table;
        @NotBlank(message = "user can't be empty or null")
        private String user;
        @NotBlank(message = "password can't be empty or null")
        private String password;
        private Mapping mapping;
    }

    @Getter
    @Setter
    @Validated
    public static class Mapping {
        @NotBlank(message = "mapping id can't be empty or null")
        private String id;
        @NotBlank(message = "mapping username can't be empty or null")
        private String username;
        @NotBlank(message = "mapping name can't be empty or null")
        private String name;
        @NotBlank(message = "mapping surname can't be empty or null")
        private String surname;
    }
}