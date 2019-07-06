package com.assignment.cs.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserOutDTO
{
    @NotNull
    private String name;
    private Integer id;
}
