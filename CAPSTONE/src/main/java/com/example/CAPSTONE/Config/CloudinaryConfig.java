package com.example.CAPSTONE.Config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class CloudinaryConfig {
    @Bean
    public Cloudinary getCloudinary(@Value("${cloudinary.cloud_name}") String name,
                                          @Value("${cloudinary.api_key}") String key,
                                          @Value("${cloudinary.api_secret}") String secret) {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", name,
                "api_key", key,
                "api_secret", secret));
    }
}
