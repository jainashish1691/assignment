package com.assignment.cs.db.dao;

import com.assignment.cs.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<UserEntity, Integer>
{
}
