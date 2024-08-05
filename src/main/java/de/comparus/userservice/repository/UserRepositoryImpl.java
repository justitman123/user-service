package de.comparus.userservice.repository;

import de.comparus.userservice.config.DataSourceConfig;
import de.comparus.userservice.config.DataSourcesPropertiesHandler;
import de.comparus.userservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;

    @Autowired
    public UserRepositoryImpl(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public List<User> fetchUsers(String username, String name, String surname) {
        List<User> allUsers = new ArrayList<>();
        for (DataSourcesPropertiesHandler.DataSourceConfigProperties dataSource : dataSourceConfig.getDataSourcesPropertiesHandler().getDataSources()) {
            JdbcTemplate jdbcTemplate = dataSourceConfig.getCustomJdbcTemplates().get(dataSource.getName());
            if (jdbcTemplate == null) {
                log.warn("Such {} datasource doesn't exist", dataSource.getName());
            } else {
                String sql = "SELECT * FROM " + dataSource.getTable() + " WHERE 1=1";
                List<Object> params = new ArrayList<>();

                if (username != null) {
                    sql += " AND " + dataSource.getMapping().getUsername() + " = ?";
                    params.add(username);
                }
                if (name != null) {
                    sql += " AND " + dataSource.getMapping().getName() + " = ?";
                    params.add(name);
                }
                if (surname != null) {
                    sql += " AND " + dataSource.getMapping().getSurname() + " = ?";
                    params.add(surname);
                }
                try {
                    allUsers.addAll(jdbcTemplate.query(sql, params.toArray(), (rs, rowNum) -> {
                        User user = new User();
                        user.setId(rs.getString(dataSource.getMapping().getId()));
                        user.setUsername(rs.getString(dataSource.getMapping().getUsername()));
                        user.setName(rs.getString(dataSource.getMapping().getName()));
                        user.setSurname(rs.getString(dataSource.getMapping().getSurname()));
                        return user;
                    }));
                } catch (Exception e) {
                    log.warn("Not done. Check the query {}", sql);
                }
            }
        }
        return allUsers;
    }
}
