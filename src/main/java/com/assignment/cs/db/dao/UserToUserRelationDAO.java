package com.assignment.cs.db.dao;

import com.assignment.cs.db.entity.UserEntity;
import com.assignment.cs.db.entity.UserToUserRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserToUserRelationDAO extends JpaRepository<UserToUserRelationEntity, Integer>
{
    List<UserToUserRelationEntity> findAllByFollower(final UserEntity follower);
}
