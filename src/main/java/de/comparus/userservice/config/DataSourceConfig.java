package de.comparus.userservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class DataSourceConfig {

    private final DataSourcesPropertiesHandler dataSourcesPropertiesHandler;
    private final Map<String, JdbcTemplate> customJdbcTemplates = new HashMap<>();

    @Autowired
    public DataSourceConfig(DataSourcesPropertiesHandler dataSourcesPropertiesHandler) {
        this.dataSourcesPropertiesHandler = dataSourcesPropertiesHandler;
        initializeDataSourceJdbcTemplateConnections(dataSourcesPropertiesHandler);
    }

    public void initializeDataSourceJdbcTemplateConnections(DataSourcesPropertiesHandler dataSourcesPropertiesHandler) {
        for (DataSourcesPropertiesHandler.DataSourceConfigProperties config : dataSourcesPropertiesHandler.getDataSources()) {
            DataSource dataSource = createDataSource(config);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            customJdbcTemplates.put(config.getName(), jdbcTemplate);
        }
    }

    private DataSource createDataSource(DataSourcesPropertiesHandler.DataSourceConfigProperties config) {
        return DataSourceBuilder.create()
                .driverClassName(getDriverClassName(config.getStrategy()))
                .url(config.getUrl())
                .username(config.getUser())
                .password(config.getPassword())
                .build();
    }

    private String getDriverClassName(String strategy) {
        return switch (strategy.toLowerCase()) {
            case "postgres" -> "org.postgresql.Driver";
            case "mysql" -> "com.mysql.cj.jdbc.Driver";
            case "oracle" -> "oracle.jdbc.OracleDriver";
            case "mssql" -> "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            default -> throw new IllegalArgumentException("Unknown database strategy: " + strategy);
        };
    }
}
