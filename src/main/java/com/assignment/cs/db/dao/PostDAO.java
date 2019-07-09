package com.assignment.cs.db.dao;

import com.assignment.cs.db.entity.PostEntity;
import com.assignment.cs.db.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostDAO extends JpaRepository<PostEntity, Integer>
{
    List<PostEntity> findAllByUserInOrderByTimeStampDesc(final List<UserEntity> users, final Pageable pageable);

    /*
    finds all post of users and its followee , sort them in descending order and fetch top 20 result
     */
    List<PostEntity> findTop20ByUserInOrderByTimeStampDesc(final List<UserEntity> users);

}
