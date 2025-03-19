package org.example.bookingbe.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getCloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dwxxsfyej");
        config.put("api_key", "542592826161616");
        config.put("api_secret", "o4J6in895bc3zWtPatPColzfbY4");
        config.put("secure", "true");
        return new Cloudinary(config);
    }
}