package app.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import app.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateUser() throws Exception {
        UserDto user = UserDto.builder().email("john.doe@hotmail.com").name("John").build();
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"john.doe@hotmail.com\",\"name\":\"John\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    public void testCreateUserWithDuplicateEmail() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"maria_128@gmail.com\",\"name\":\"Jane\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testCreateUserWithInvalidEmail() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"mariagmail.com\",\"name\":\"Jane\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get("/api/v1/users/2")
                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserWithInvalidId() throws Exception {
        mockMvc.perform(get("/api/v1/users/11")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/users/1")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteInvalidUser() throws Exception {
        mockMvc.perform(delete("/api/v1/users/11")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(patch("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Updated\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUserWithDuplicateEmail() throws Exception {
        mockMvc.perform(patch("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"maria_128@gmail.com\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    public void testSearchUsersByName() throws Exception {
        mockMvc.perform(get("/api/v1/users/search?keyword=Jane&pageNo=0&pageSize=10")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0]..email").value("maria_128@gmail.com"))
                .andExpect(jsonPath("$[0]..name").value("Jane"));
    }

    @Test
    public void testSearchUsersByEmail() throws Exception {
        mockMvc.perform(get("/api/v1/users/search?keyword=maria_12&pageNo=0&pageSize=10")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0]..email").value("maria_128@gmail.com"))
                .andExpect(jsonPath("$[0]..name").value("Jane"));
    }
}
