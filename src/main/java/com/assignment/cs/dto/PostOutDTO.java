package com.assignment.cs.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class PostOutDTO
{
    @NotNull
    private String content;
    private Integer id;
    private String createdBy;
    private LocalDateTime timeStamp;
}
