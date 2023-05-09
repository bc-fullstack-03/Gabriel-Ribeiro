package com.sysmap.parrot.controller;
import com.sysmap.parrot.entities.Post;
import com.sysmap.parrot.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PostControllerTests {
    private PostController postController;
    private PostService postService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        postService = mock(PostService.class);
        postController = new PostController(postService);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void testFetchAllPosts() throws Exception {
        Post post1 = new Post();
        post1.setId("1");
        post1.setTitle("title1");
        post1.setDescription("description1");
        Post post2 = new Post();
        post2.setId("2");
        post2.setTitle("title2");
        post2.setDescription("description2");
        when(postService.getAllPosts()).thenReturn(Arrays.asList(post1, post2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("title1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("description1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("title2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("description2"));

        verify(postService, times(1)).getAllPosts();
        verifyNoMoreInteractions(postService);
    }
}
