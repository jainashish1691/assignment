package com.assignment.cs.business.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
@Slf4j
public class BadRequestException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public BadRequestException()
    {
        super();
        log.error("INVALID DATA");

    }
}
