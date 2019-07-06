/*
package com.assignment.cs.config;

import de.infinnit.commons.business.jackson.JacksonObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter
{

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
    {
        // @formatter:off
        configurer
            .favorPathExtension(false)
            .favorParameter(true)
            .parameterName("mediaType")
            .ignoreAcceptHeader(true)
            .useJaf(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON);
        // @formatter:on
    }

    @Bean
    public MappingJackson2HttpMessageConverter converter()
    {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new JacksonObjectMapper());
        return converter;
    }
}
*/
