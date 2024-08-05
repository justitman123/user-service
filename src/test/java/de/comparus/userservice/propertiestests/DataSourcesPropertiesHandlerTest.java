package de.comparus.userservice.propertiestests;

import de.comparus.userservice.config.CustomPropertyLoaderFactory;
import de.comparus.userservice.config.DataSourcesPropertiesHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = DataSourcesPropertiesHandler.class)
@TestPropertySource(locations = "classpath:test-datasource-properties.yml", factory = CustomPropertyLoaderFactory.class)
public class DataSourcesPropertiesHandlerTest {

    @Autowired
    private DataSourcesPropertiesHandler dataSourcesPropertiesHandler;

    @Test
    public void testDataSourcesPropertiesInjection() {
        assertThat(dataSourcesPropertiesHandler.getDataSources().isEmpty()).isFalse();
        assertThat(dataSourcesPropertiesHandler.getDataSources().size()).isEqualTo(2);

        DataSourcesPropertiesHandler.DataSourceConfigProperties dataSource1 = dataSourcesPropertiesHandler.getDataSources().get(0);
        assertThat(dataSource1.getName()).isEqualTo("data-base-1-test");
        assertThat(dataSource1.getStrategy()).isEqualTo("postgres");
        assertThat(dataSource1.getUrl()).isEqualTo("jdbc:postgresql://localhost:5432/database1");
        assertThat(dataSource1.getTable()).isEqualTo("users");
        assertThat(dataSource1.getUser()).isEqualTo("testuser");
        assertThat(dataSource1.getPassword()).isEqualTo("testpass");
        assertThat(dataSource1.getMapping().getId()).isEqualTo("user_id");
        assertThat(dataSource1.getMapping().getUsername()).isEqualTo("login");
        assertThat(dataSource1.getMapping().getName()).isEqualTo("first_name");
        assertThat(dataSource1.getMapping().getSurname()).isEqualTo("last_name");

        DataSourcesPropertiesHandler.DataSourceConfigProperties dataSource2 = dataSourcesPropertiesHandler.getDataSources().get(1);
        assertThat(dataSource2.getName()).isEqualTo("test-test-test-data-base-2");
        assertThat(dataSource2.getStrategy()).isEqualTo("mysql");
        assertThat(dataSource2.getUrl()).isEqualTo("jdbc:mysql://localhost:3306/database2");
        assertThat(dataSource2.getTable()).isEqualTo("user_table");
        assertThat(dataSource2.getUser()).isEqualTo("testuser");
        assertThat(dataSource2.getPassword()).isEqualTo("testpass");
        assertThat(dataSource2.getMapping().getId()).isEqualTo("id");
        assertThat(dataSource2.getMapping().getUsername()).isEqualTo("ldap_login");
        assertThat(dataSource2.getMapping().getName()).isEqualTo("name");
        assertThat(dataSource2.getMapping().getSurname()).isEqualTo("surname");
    }
}
