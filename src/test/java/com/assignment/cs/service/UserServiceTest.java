package com.assignment.cs.service;

import com.assignment.cs.business.exception.BadRequestException;
import com.assignment.cs.business.exception.NotFoundException;
import com.assignment.cs.business.service.UserService;
import com.assignment.cs.business.service.impl.UserServiceImpl;
import com.assignment.cs.db.dao.PostDAO;
import com.assignment.cs.db.dao.UserDAO;
import com.assignment.cs.db.dao.UserToUserRelationDAO;
import com.assignment.cs.db.entity.PostEntity;
import com.assignment.cs.db.entity.UserEntity;
import com.assignment.cs.db.entity.UserToUserRelationEntity;
import com.assignment.cs.dto.PostInDTO;
import com.assignment.cs.dto.PostOutDTO;
import com.assignment.cs.dto.UserInDTO;
import com.assignment.cs.dto.UserOutDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTest
{

    @InjectMocks
    private UserService userService;

    private UserDAO userDAO;

    private PostDAO postDAO;

    private UserToUserRelationDAO userToUserRelationDAO;

    @Before
    public void initMock()
    {
        userDAO = Mockito.mock(UserDAO.class);
        postDAO = Mockito.mock(PostDAO.class);
        userToUserRelationDAO = Mockito.mock(UserToUserRelationDAO.class);

        userService = new UserServiceImpl(userDAO, postDAO, userToUserRelationDAO);
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NotFoundException.class)
    public void UST1_getUserWhenNoUserPresent()
    {
        Mockito.when(userDAO.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        userService.getUser(1);
    }

    @Test(expected = NotFoundException.class)
    public void UST2_createPostWhenNoUserPresent()
    {
        Mockito.when(userDAO.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        userService.createPost(1, new PostInDTO());
    }

    @Test(expected = BadRequestException.class)
    public void UST3_followUserIfSameUserId()
    {
        userService.follow(1, 1);
    }

    @Test(expected = BadRequestException.class)
    public void UST4_followUserIfUserAlreadyFollowingFollowee()
    {
        final UserEntity userEntity = new UserEntity();
        Mockito.when(userDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(userEntity));
        Mockito.when(userToUserRelationDAO.findByFollowerAndFollowee(userEntity, userEntity)).thenReturn(Optional.of(new UserToUserRelationEntity()));
        userService.follow(1, 2);

    }

    @Test
    public void UST5_createPostSuccess()
    {
        final PostEntity post = getMockedPostEntity();
        final UserEntity user = mockUserEntity();
        Mockito.when(userDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(postDAO.save(Mockito.any(PostEntity.class))).thenReturn(post);
        final PostOutDTO postOutDto = userService.createPost(1, new PostInDTO());
        Assert.assertNotNull(postOutDto);
    }

    @Test
    public void UST6_createUserSuccess()
    {
        final UserEntity user = mockUserEntity();
        final UserInDTO userInDto = new UserInDTO();
        userInDto.setName("Ashish");
        Mockito.when(userDAO.save(Mockito.any(UserEntity.class))).thenReturn(user);
        final UserOutDTO userOut = userService.createUser(userInDto);
        Assert.assertNotNull(userOut);
    }

    @Test
    public void UST7_followUserSuccess()
    {
        final UserEntity user = mockUserEntity();
        Mockito.when(userDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(userToUserRelationDAO.findByFollowerAndFollowee(user, user)).thenReturn(Optional.empty());
        userService.follow(1, 2);
        verify(userToUserRelationDAO, times(1)).save(Mockito.any(UserToUserRelationEntity.class));

    }

    @Test
    public void UST8_unFollowUserSuccess()
    {
        final UserEntity user = mockUserEntity();
        Mockito.when(userDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(userToUserRelationDAO.findByFollowerAndFollowee(user, user)).thenReturn(Optional.of(new UserToUserRelationEntity()));
        userService.unFollow(1, 2);
        verify(userToUserRelationDAO, times(1)).delete(Mockito.any(UserToUserRelationEntity.class));

    }

    private PostEntity getMockedPostEntity()
    {
        final PostEntity mockedPostEntity = new PostEntity();
        final UserEntity user = mockUserEntity();
        mockedPostEntity.setTimeStamp(LocalDateTime.MAX);
        mockedPostEntity.setUser(user);
        mockedPostEntity.setContents("test");
        return mockedPostEntity;
    }

    private UserEntity mockUserEntity()
    {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Ashish");
        return userEntity;
    }
}
