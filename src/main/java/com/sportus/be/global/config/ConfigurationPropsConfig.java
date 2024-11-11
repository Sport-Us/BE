package com.sportus.be.global.config;

import com.sportus.be.auth.application.type.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {JwtProperties.class})
public class ConfigurationPropsConfig {
}
