package com.sportus.be.global.config;

import com.sportus.be.auth.application.type.JwtProperties;
import com.sportus.be.recommend.application.type.ClovaStudioProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {JwtProperties.class, ClovaStudioProperties.class})
public class ConfigurationPropsConfig {
}
