package com.assignment.cs.controller;

import com.assignment.cs.business.service.UserService;
import com.assignment.cs.dto.PostInDTO;
import com.assignment.cs.dto.PostOutDTO;
import com.assignment.cs.dto.UserInDTO;
import com.assignment.cs.dto.UserOutDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping
    @ResponseBody
    public UserOutDTO createUser(@RequestBody @Valid UserInDTO userDTO)
    // @formatter:on
    {
        return userService.createUser(userDTO);
    }

    @GetMapping(value = "/{userId}")
    @ResponseBody
    public UserOutDTO getUser(@PathVariable @NotNull Integer userId)
    // @formatter:on
    {
        return userService.getUser(userId);
    }

    @PostMapping(value = "/{userId}/post")
    @ResponseBody
    public PostOutDTO createPost(@PathVariable Integer userId, @RequestBody @Valid PostInDTO postInDTO)
    // @formatter:on
    {
        return userService.createPost(userId, postInDTO);
    }

    @PostMapping(value = "/{userId}/follow/{followeeId}")
    @ResponseBody
    public void follow(@PathVariable Integer userId, @PathVariable @NotNull Integer followeeId)
    // @formatter:on
    {
        userService.follow(userId, followeeId);
    }

    @GetMapping(value = "/{userId}/newsFeeds")
    @ResponseBody
    public List<PostOutDTO> getMostRecentNewsFeed(@PathVariable @NotNull Integer userId)
    // @formatter:on
    {
        return userService.getMostRecentPost(userId);
    }

    @DeleteMapping(value = "/{userId}/unFollow/{followeeId}")
    @ResponseBody
    public void unFollow(@PathVariable @NotNull Integer userId, @PathVariable @NotNull Integer followeeId)
    // @formatter:on
    {
        userService.unFollow(userId, followeeId);
    }

}
