package com.zadanie.socialnetwork;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zadanie.socialnetwork.post.CreateSocialNetworkPostRequest;
import com.zadanie.socialnetwork.post.SocialNetworkPostDTO;
import com.zadanie.socialnetwork.post.UpdateSocialNetworkPostRequest;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class SocialNetworkPostTests {

    @Autowired
    private MockMvc mockMvc;

    private final String POST_CONTENT = "Post Content";
    private final String TEST_AUTHOR = "TEST_AUTHOR";
    private final String TEST_CONTENT = "TEST_CONTENT";
    private final String UPDATED_CONTENT = "UPDATED_CONTENT";

    public Long createPost() throws Exception {
        CreateSocialNetworkPostRequest request = new CreateSocialNetworkPostRequest();
        request.setAuthor(TEST_AUTHOR);
        request.setContent(TEST_CONTENT);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk()).andReturn();
        return Long.valueOf(result.getResponse().getContentAsString());
    }

    @Test
    public void testGetPost() throws Exception {
        Long id = createPost();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/post/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(TEST_AUTHOR))
                .andExpect(jsonPath("$.content").value(TEST_CONTENT));
    }

    @Test
    public void testUpdatePost() throws Exception {
        Long id = createPost();
        UpdateSocialNetworkPostRequest request = new UpdateSocialNetworkPostRequest();
        request.setContent(UPDATED_CONTENT);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/post/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(UPDATED_CONTENT));
    }

    @Test
    public void testDeletePost() throws Exception {
        Long id = createPost();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/post/{id}", id))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/post/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Sql("/data.sql")
    @Test
    public void testGetPageOfPosts() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/post"))
                .andExpect(status().isOk()).andReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        String response = result.getResponse().getContentAsString();
        JSONObject json = new JSONObject(response);
        SocialNetworkPostDTO[] dto = objectMapper.readValue(json.get("content").toString(), SocialNetworkPostDTO[].class);

        Assertions.assertThat(dto.length == 15);

        Assertions.assertThat(dto[0].getAuthor().equals("A"));
        Assertions.assertThat(dto[0].getContent().equals(POST_CONTENT));
        Assertions.assertThat(dto[0].getViewCount().intValue() == 1);

        Assertions.assertThat(dto[5].getAuthor().equals("B"));
        Assertions.assertThat(dto[5].getContent().equals(POST_CONTENT));
        Assertions.assertThat(dto[5].getViewCount().intValue() == 5780);

        Assertions.assertThat(dto[14].getAuthor().equals("D"));
        Assertions.assertThat(dto[14].getContent().equals(POST_CONTENT));
        Assertions.assertThat(dto[14].getViewCount().intValue() == 56780);
    }

    @Sql("/data.sql")
    @Test
    public void testGetTopOfThePosts() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/post/top"))
                .andExpect(status().isOk()).andReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        SocialNetworkPostDTO[] dto = objectMapper.readValue(result.getResponse().getContentAsString(), SocialNetworkPostDTO[].class);

        Assertions.assertThat(dto.length == 10);
        Assertions.assertThat(dto[0].getAuthor().equals("C"));
        Assertions.assertThat(dto[0].getContent().equals(POST_CONTENT));
        Assertions.assertThat(dto[0].getViewCount().intValue() == 678678);

        Assertions.assertThat(dto.length == 10);
        Assertions.assertThat(dto[9].getAuthor().equals("B"));
        Assertions.assertThat(dto[9].getContent().equals(POST_CONTENT));
        Assertions.assertThat(dto[9].getViewCount().intValue() == 789);


    }


}
