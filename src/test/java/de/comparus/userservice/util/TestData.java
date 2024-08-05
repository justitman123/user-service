package de.comparus.userservice.util;

import de.comparus.userservice.model.User;

import java.util.ArrayList;
import java.util.List;

public class TestData {
    public static final User USER_1 = new User("212asdd", "some__username", "some_name1", "some_surname1");
    public static final User USER_2 = new User("21231", "some_username1", "some_name1", "some_surname");
    public static final User USER_3 = new User("21233", "some_username1", "some_name", "some_surname1");
    public static final User USER_4 = new User("212asdd1", "some_username1", "some_name1", "some_surname1");
    public static final User USER_5 = new User("212312", "some_username2", "some_name1", "some_surname");

    public static final User USER_6 = new User("1", "some_username1", "John", "some_surname1");
    public static final User USER_7 = new User("2", "some_username1", "Alice", "some_surname");
    public static final User USER_8 = new User("3", "bjones", "some_name1", "some_surname1");
    public static final User USER_9 = new User("4", "cjohnson", "Carol", "Johnson");
    public static final User USER_10 = new User("5", "dlee", "David", "Lee");

    public static final List<User> POSTGRES_USERS = List.of(USER_1,
            USER_2,
            USER_3,
            USER_4,
            USER_5);

    public static final List<User> MYSQL_USERS = List.of(USER_6,
            USER_7,
            USER_8,
            USER_9,
            USER_10);

    public static final List<User> ALL_USERS;

    static {
        ALL_USERS = new ArrayList<>();
        ALL_USERS.addAll(POSTGRES_USERS);
        ALL_USERS.addAll(MYSQL_USERS);
    }
}
