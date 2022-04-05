package com.example.spring.crud.phrase;

import com.example.spring.crud.phrase.properties.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class SpringCrudPhraseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCrudPhraseApplication.class, args);
    }

}
