package com.assignment.cs.business.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
@Slf4j
public class NotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public NotFoundException()
    {
        super();
        log.error("Data not found");

    }
}
