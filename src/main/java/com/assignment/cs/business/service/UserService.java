package com.assignment.cs.business.service;

import com.assignment.cs.dto.PostInDTO;
import com.assignment.cs.dto.PostOutDTO;
import com.assignment.cs.dto.UserInDTO;
import com.assignment.cs.dto.UserOutDTO;

import java.util.List;

public interface UserService
{
    UserOutDTO createUser(final UserInDTO userInDTO);

    UserOutDTO getUser(final Integer userId);

    PostOutDTO createPost(final Integer userId, final PostInDTO postInDTO);

    void follow(final Integer followerId, final Integer followeeId);

    List<PostOutDTO> finMostRecentPost(final Integer userID);
}
