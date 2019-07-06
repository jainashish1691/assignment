package com.assignment.cs.db.dao;

import com.assignment.cs.db.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDAO extends JpaRepository<PostEntity, Integer>
{

}
