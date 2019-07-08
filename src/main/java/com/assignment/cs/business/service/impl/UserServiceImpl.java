package com.assignment.cs.business.service.impl;

import com.assignment.cs.business.exception.BadRequestException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
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
    public void follow(final Integer followerId, final Integer followeeId)
    {

        if (followerId.equals(followeeId))
        {
            log.error("Follower And followee Cannot Be Same");
            throw new BadRequestException();
        }

        final Optional<UserEntity> followerOptional = userDAO.findById(followerId);
        final UserEntity follower = followerOptional.orElseThrow(NotFoundException::new);

        final Optional<UserEntity> followeeOptional = userDAO.findById(followeeId);
        final UserEntity followee = followeeOptional.orElseThrow(NotFoundException::new);

        validateIfUserAlreadyFollowingFollowee(follower, followee);

        final UserToUserRelationEntity userToUserRelationEntity = new UserToUserRelationEntity();
        userToUserRelationEntity.setFollower(follower);
        userToUserRelationEntity.setFollowee(followee);
        userToUserRelationDAO.save(userToUserRelationEntity);
        log.info("User successfully followed ");

    }

    @Override
    public void unFollow(final Integer followerId, final Integer followeeId)
    {
        final UserEntity follower = getUserById(followerId);
        final UserEntity followee = getUserById(followeeId);
        final Optional<UserToUserRelationEntity> optional = userToUserRelationDAO.findByFollowerAndFollowee(follower, followee);
        final UserToUserRelationEntity userToUserRelation = optional.orElseThrow(NotFoundException::new);
        userToUserRelationDAO.delete(userToUserRelation);
    }

    @Override
    public List<PostOutDTO> getMostRecentPost(final Integer userID)
    {
        final Optional<UserEntity> userOptional = userDAO.findById(userID);

        final UserEntity user = userOptional.orElseThrow(NotFoundException::new);

        final List<UserToUserRelationEntity> allByFollower = userToUserRelationDAO.findAllByFollower(user);

        //followee list of user which user follows
        final List<UserEntity> followeeList = allByFollower.stream().map(UserToUserRelationEntity::getFollowee).collect(Collectors.toList());

        //combined list of user and its followee's
        final List<UserEntity> userAndFollowee = new ArrayList<>();
        userAndFollowee.add(user);
        userAndFollowee.addAll(followeeList);

        //finds all post of users , sort them in descending order and fetch top 20 result
        final List<PostEntity> mostRecentFeeds = postDAO.findTop20ByUserInOrderByTimeStampDesc(userAndFollowee);

        return createOutDTOList(mostRecentFeeds);
    }

    private UserEntity getUserById(final Integer userId)
    {
        final Optional<UserEntity> userOptional = userDAO.findById(userId);
        return userOptional.orElseThrow(NotFoundException::new);
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

    private void validateIfUserAlreadyFollowingFollowee(final UserEntity follower, final UserEntity followee)
    {
        final Optional<UserToUserRelationEntity> optional = userToUserRelationDAO.findByFollowerAndFollowee(follower, followee);
        if (optional.isPresent())
        {
            log.error("User is already following given followee");
            throw new BadRequestException();
        }

    }

}
