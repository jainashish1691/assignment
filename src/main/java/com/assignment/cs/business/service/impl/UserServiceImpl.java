package com.assignment.cs.business.service.impl;

import com.assignment.cs.business.exception.NotFoundException;
import com.assignment.cs.business.service.UserService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService
{
    private final UserDAO userDAO;

    private final PostDAO postDAO;

    private final UserToUserRelationDAO userToUserRelationDAO;

    public UserServiceImpl(UserDAO userDAO, PostDAO postDAO, UserToUserRelationDAO userToUserRelationDAO)
    {
        this.userDAO = userDAO;
        this.postDAO = postDAO;
        this.userToUserRelationDAO = userToUserRelationDAO;
    }

    @Override
    public UserOutDTO createUser(final UserInDTO userInDTO)
    {
        final UserEntity user = new UserEntity();
        user.setName(userInDTO.getName());
        userDAO.save(user);
        return mapUserToOutDto(user);
    }

    @Override
    public UserOutDTO getUser(final Integer userId)
    {
        final Optional<UserEntity> userOptional = userDAO.findById(userId);
        final UserEntity user = userOptional.orElseThrow(NotFoundException::new);
        return mapUserToOutDto(user);
    }

    @Override
    public PostOutDTO createPost(final Integer userId, final PostInDTO postInDTO)
    {
        final Optional<UserEntity> userOptional = userDAO.findById(userId);
        final UserEntity user = userOptional.orElseThrow(NotFoundException::new);
        final PostEntity post = new PostEntity();
        post.setContents(postInDTO.getContents());
        post.setUser(user);
        post.setTimeStamp(LocalDateTime.now());
        postDAO.save(post);
        return mapPostToOutDto(post, user);
    }

    @Override
    public void follow(Integer followerId, Integer followeeId)
    {
        final Optional<UserEntity> followerOptional = userDAO.findById(followerId);
        final UserEntity follower = followerOptional.orElseThrow(NotFoundException::new);

        final Optional<UserEntity> followeeOptional = userDAO.findById(followeeId);
        final UserEntity followee = followeeOptional.orElseThrow(NotFoundException::new);

        final UserToUserRelationEntity userToUserRelationEntity = new UserToUserRelationEntity();
        userToUserRelationEntity.setFollower(follower);
        userToUserRelationEntity.setFollowee(followee);
        userToUserRelationDAO.save(userToUserRelationEntity);

    }

    @Override
    public List<PostOutDTO> finMostRecentPost(final Integer userID)
    {
        final Optional<UserEntity> userOptional = userDAO.findById(userID);

        final UserEntity user = userOptional.orElseThrow(NotFoundException::new);
        final List<PostEntity> posts = user.getPosts();        //posts of the user

        final List<UserToUserRelationEntity> allByFollower = userToUserRelationDAO.findAllByFollower(user);

        //followee list of user
        final List<UserEntity> followeeList = allByFollower.stream().map(UserToUserRelationEntity::getFollowee).collect(Collectors.toList());

        final List<List<PostEntity>> collect = followeeList.stream().map(UserEntity::getPosts).collect(Collectors.toList());
        final List<PostEntity> postsByFollowee = collect.stream().flatMap(Collection::stream).collect(Collectors.toList());// posts of all the followee

        posts.addAll(postsByFollowee);//adding all the posts of followee to existing post of user

        //sort post with time stamps
        posts.sort((PostEntity p1, PostEntity p2) -> p2.getTimeStamp().compareTo(p1.getTimeStamp()));

        //limit the result to max size of 20
        final List<PostEntity> mostRecentFeeds = posts.stream().limit(20).collect(Collectors.toList());

        return createOutDTOList(mostRecentFeeds);
    }

    private List<PostOutDTO> createOutDTOList(final List<PostEntity> mostRecentFeeds)
    {
        final List<PostOutDTO> posts = new ArrayList<>();
        mostRecentFeeds.forEach(postEntity -> posts.add(mapPostToOutDto(postEntity, postEntity.getUser())));
        return posts;
    }

    private UserOutDTO mapUserToOutDto(final UserEntity userEntity)
    {
        final UserOutDTO userOutDTO = new UserOutDTO();
        userOutDTO.setId(userEntity.getId());
        userOutDTO.setName(userEntity.getName());
        return userOutDTO;
    }

    private PostOutDTO mapPostToOutDto(final PostEntity post, final UserEntity userEntity)
    {
        final PostOutDTO postOutDTO = new PostOutDTO();
        postOutDTO.setContent(post.getContents());
        postOutDTO.setId(post.getId());
        postOutDTO.setCreatedBy(userEntity.getName());
        postOutDTO.setTimeStamp(post.getTimeStamp());
        return postOutDTO;
    }
}
