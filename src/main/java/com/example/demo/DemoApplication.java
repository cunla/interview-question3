package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author      Dmitry Baranau (dbaranau@gmail.com)
 * @version     1.0
 *  Main application class to start the application
 *  A basic spring-boot app to demonstrate exposing a set of REST endpoints to retreive and add messages and replies. That can be used as a template to develop a forum system.
 */
@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.example.demo.entity"})  // scan JPA entities
public class DemoApplication {

    /**
     * Program entry point
     * @param args  Optional console input parameters
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
