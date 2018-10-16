package com.cleartrip.configuration;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.cleartrip")
@PropertySource(value = { "classpath:config/config.properties" })
public class ContextConfiguration {

}
