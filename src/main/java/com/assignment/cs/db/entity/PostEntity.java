package com.assignment.cs.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString()
@Entity
@Table(name = "post")
public class PostEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "contents")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "ref_user")
    private UserEntity user;

    @Column(name = "timestamp")
    private LocalDateTime timeStamp;


}