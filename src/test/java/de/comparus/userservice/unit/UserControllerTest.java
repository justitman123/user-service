package de.comparus.userservice.unit;

import de.comparus.userservice.controller.UserController;
import de.comparus.userservice.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.comparus.userservice.util.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Test
    void testGetEmptyList() throws Exception {
        when(userServiceImpl.getAllUsers(null, null, null))
                .thenReturn(Collections.EMPTY_LIST);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void testGetAll() throws Exception {
        when(userServiceImpl.getAllUsers(null, null, null))
                .thenReturn(TestData.ALL_USERS);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(TestData.ALL_USERS)));
    }

    @Test
    void testGetWithUserNameFilter() throws Exception {
        when(userServiceImpl.getAllUsers("some_username1", null, null))
                .thenReturn(List.of(TestData.USER_2,
                        TestData.USER_3,
                        TestData.USER_6,
                        TestData.USER_7));
        mockMvc.perform(get("/users").param("username", "some_username1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(TestData.USER_2,
                        TestData.USER_3,
                        TestData.USER_6,
                        TestData.USER_7))));
    }

    @Test
    void testGetWithNameFilter() throws Exception {
        when(userServiceImpl.getAllUsers(null, "some_name1", null))
                .thenReturn(List.of(TestData.USER_1,
                        TestData.USER_2,
                        TestData.USER_4,
                        TestData.USER_5));
        mockMvc.perform(get("/users").param("name", "some_name1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(TestData.USER_1,
                        TestData.USER_2,
                        TestData.USER_4,
                        TestData.USER_5))));
    }

    @Test
    void testGetWithSurnameFilter() throws Exception {
        when(userServiceImpl.getAllUsers(null, null, "some_surname1"))
                .thenReturn(List.of(TestData.USER_1,
                        TestData.USER_3,
                        TestData.USER_4,
                        TestData.USER_6,
                        TestData.USER_8));
        mockMvc.perform(get("/users").param("surname", "some_surname1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(TestData.USER_1,
                        TestData.USER_3,
                        TestData.USER_4,
                        TestData.USER_6,
                        TestData.USER_8))));
    }
}
