package de.comparus.userservice.integration;

import de.comparus.userservice.config.CustomPropertyLoaderFactory;
import de.comparus.userservice.config.DataSourcesPropertiesHandler;
import de.comparus.userservice.model.User;
import de.comparus.userservice.util.TestData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties(value = DataSourcesPropertiesHandler.class)
@TestPropertySource(value = "classpath:test-datasource-properties.yml", factory = CustomPropertyLoaderFactory.class)
public class UserControllerTest {

    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:latest").withDatabaseName("data-base-1-test");
    private static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:5.7").withDatabaseName("test-test-test-data-base-2");

    @BeforeAll
    static void beforeAll() {
        POSTGRES.withInitScript("db/init_postgres.sql");
        POSTGRES.start();

        MYSQL.withInitScript("db/init_mysql.sql");
        MYSQL.start();
    }

    @AfterAll
    static void afterAll() {
        POSTGRES.stop();
        MYSQL.stop();
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @DynamicPropertySource
    static void setDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("data-sources[0].name", () -> "data-base-1-test");
        registry.add("data-sources[0].strategy", () -> "postgres");
        registry.add("data-sources[0].url", POSTGRES::getJdbcUrl);
        registry.add("data-sources[0].table", () -> "users");
        registry.add("data-sources[0].user", POSTGRES::getUsername);
        registry.add("data-sources[0].password", POSTGRES::getPassword);
        registry.add("data-sources[0].mapping.id", () -> "user_id");
        registry.add("data-sources[0].mapping.username", () -> "login");
        registry.add("data-sources[0].mapping.name", () -> "first_name");
        registry.add("data-sources[0].mapping.surname", () -> "last_name");

        registry.add("data-sources[1].name", () -> "test-test-test-data-base-2");
        registry.add("data-sources[1].strategy", () -> "mysql");
        registry.add("data-sources[1].url", MYSQL::getJdbcUrl);
        registry.add("data-sources[1].table", () -> "user_table");
        registry.add("data-sources[1].user", MYSQL::getUsername);
        registry.add("data-sources[1].password", MYSQL::getPassword);
        registry.add("data-sources[1].mapping.id", () -> "id");
        registry.add("data-sources[1].mapping.username", () -> "ldap_login");
        registry.add("data-sources[1].mapping.name", () -> "name");
        registry.add("data-sources[1].mapping.surname", () -> "surname");
    }

    @Test
    void testGetAll() {
        ResponseEntity<List<User>> response = restTemplate
                .exchange("/users", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });
        List<User> responseApi = response.getBody();
        Assertions.assertIterableEquals(responseApi, TestData.ALL_USERS);
    }

    @Test
    public void testGetUsersByUsername() {
        String username = "some_username1";
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/users?username=" + username,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        List<User> users = response.getBody();
        assertThat(users).isNotNull();
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getUsername()).isEqualTo(username);
        assertThat(users.get(0).getName()).isEqualTo("some_name1");
        assertThat(users.get(0).getSurname()).isEqualTo("some_surname");
    }

    @Test
    public void testGetUsersByName() {
        String name = "John";
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/users?name=" + name,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        List<User> users = response.getBody();
        assertThat(users).isNotNull();
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getName()).isEqualTo(name);
        assertThat(users.get(0).getSurname()).isEqualTo("some_surname1");
    }

    @Test
    public void testGetUsersBySurname() {
        String surname = "Johnson";
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/users?surname=" + surname,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        List<User> users = response.getBody();
        assertThat(users).isNotNull();
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getUsername()).isEqualTo("cjohnson");
        assertThat(users.get(0).getName()).isEqualTo("Carol");
        assertThat(users.get(0).getSurname()).isEqualTo(surname);
    }

    @Test
    public void testGetUsersByUsernameAndName() {
        String username = "some_username1";
        String name = "some_name1";
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/users?username=" + username + "&name=" + name,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        List<User> users = response.getBody();
        assertThat(users).isNotNull();
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getUsername()).isEqualTo(username);
        assertThat(users.get(0).getName()).isEqualTo(name);
        assertThat(users.get(0).getSurname()).isEqualTo("some_surname");
    }

    @Test
    public void testGetUsersByNameAndSurname() {
        String name = "David";
        String surname = "Lee";
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/users?name=" + name + "&surname=" + surname,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        List<User> users = response.getBody();
        assertThat(users).isNotNull();
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getUsername()).isEqualTo("dlee");
        assertThat(users.get(0).getName()).isEqualTo(name);
        assertThat(users.get(0).getSurname()).isEqualTo(surname);
    }

    @Test
    public void testGetUsersByMixedCaseParameters() {
        String username = "SoMe_UsErNaMe1";
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/users?username=" + username,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        List<User> users = response.getBody();
        assertThat(users).isNotNull();
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getUsername().equalsIgnoreCase(username)).isTrue();
    }

    @Test
    public void testGetUsersByInvalidUsername() {
        String username = "non_existing_user";
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/users?username=" + username,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        List<User> users = response.getBody();
        assertThat(users).isNotNull();
        assertThat(users).isEmpty();
    }
}