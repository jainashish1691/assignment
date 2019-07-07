package com.assignment.cs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = { "com.assignment" })
@EntityScan(basePackages = { "com.assignment" })
public class TestApplication
{
    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(TestApplication.class);
        app.run(args);
    }

}
