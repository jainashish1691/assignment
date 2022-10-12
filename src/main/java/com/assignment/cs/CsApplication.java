package com.assignment.cs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.vault.config.VaultProperties;

@Slf4j
@SpringBootApplication
public class CsApplication implements CommandLineRunner
{
    @Autowired
    VaultProperties vaultProperties;
    @Value("${mykey}")
    String mykey;


    public static void main(String[] args)
    {
        SpringApplication.run(CsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String applicationName = vaultProperties.getApplicationName();
        log.info("keyyyyy value {}", mykey);
    }
}
