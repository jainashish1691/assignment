package com.assignment.cs.controller;

import com.assignment.cs.TestApplication;
import com.assignment.cs.business.service.UserService;
import com.assignment.cs.dto.PostInDTO;
import com.assignment.cs.dto.UserInDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestApplication.class })
@AutoConfigureMockMvc
public class UserControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper mapper;

    @Before
    public void beforeEveryTest()
    {
        mapper = new ObjectMapper();
    }

    @Test
    public void getUserSuccessfulTest() throws Exception
    {
        this.mockMvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(userService, times(1)).getUser(1);
    }

    @Test
    public void createUserWithInvalidDataTest() throws Exception
    {
        final UserInDTO userInDTO = new UserInDTO();
        userInDTO.setName(null);
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(userInDTO))).andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithValidDataTest() throws Exception
    {
        final UserInDTO userInDTO = new UserInDTO();
        userInDTO.setName("Ashish");
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(userInDTO))).andExpect(status().isOk());
    }

    @Test
    public void createPostWithInvalidDataTest() throws Exception
    {
        final PostInDTO postInDTO = new PostInDTO();
        postInDTO.setContents(null);
        this.mockMvc.perform(post("/user/1/post").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(postInDTO))).andExpect(status().isBadRequest());
    }

    @Test
    public void createPostWithValidDataTest() throws Exception
    {
        final PostInDTO postInDTO = new PostInDTO();
        postInDTO.setContents("Ashish");
        this.mockMvc.perform(post("/user/1/post").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(postInDTO))).andExpect(status().isOk());
    }

    @Test
    public void followUserWithValidDataTest() throws Exception
    {
        this.mockMvc.perform(post("/user/1/follow/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(userService, times(1)).follow(1, 2);

    }

    @Test
    public void unfollowUserWithValidDataTest() throws Exception
    {
        this.mockMvc.perform(delete("/user/1/unFollow/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(userService, times(1)).unFollow(1, 2);

    }

    @Test
    public void getMostRecentNewsFeeds() throws Exception
    {
        this.mockMvc.perform(get("/user/1/newsFeeds").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(userService, times(1)).getMostRecentPost(1);

    }

}
