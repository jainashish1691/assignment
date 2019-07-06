package com.assignment.cs.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserInDTO
{
    @NotNull
    private String name;
}
