package com.assignment.cs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.assignment.cs" })
public class CsApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(CsApplication.class, args);
    }

}
