package com.assignment.cs.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostInDTO
{
    @NotNull
    private String contents;
}
