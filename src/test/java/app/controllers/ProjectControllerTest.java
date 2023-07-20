package app.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateProject() throws Exception {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Project D\",\"description\":\"D\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("D"))
                .andExpect(jsonPath("$.name").value("Project D"));
    }

    @Test
    public void testGetProjectById() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProjectWithInvalidId() throws Exception {
        mockMvc.perform(get("/api/v1/projects/11")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteProject() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/4")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteInvalidProject() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/11")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateProject() throws Exception {
        mockMvc.perform(patch("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Project A Updated\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateInvalidProject() throws Exception {
        mockMvc.perform(patch("/api/v1/projects/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Project A Updated\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllProjects() throws Exception {
        mockMvc.perform(get("/api/v1/projects")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    public void testSearchProjectByName() throws Exception {
        mockMvc.perform(get("/api/v1/projects/search?keyword=Project&pageNo=0&pageSize=10")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }
}
