package com.assignment.cs.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString()
@Entity
@Table(name = "user_to_user")
public class UserToUserRelationEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "follower")
    private UserEntity follower;

    @OneToOne
    @JoinColumn(name = "followee")
    private UserEntity followee;


}