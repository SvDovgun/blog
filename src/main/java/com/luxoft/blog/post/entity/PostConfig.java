package com.luxoft.blog.post.entity;

import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PostConfig {

    @Bean
    CommandLineRunner commandLineRunner(PostRepository repository){
        return  args -> {
            Post title = new Post(
                    "title line",
                    "many many text of content",
                    true
            );
            Post discuss1  = new Post(
                    "JPA discussion",
                    "is JBA better of JDBC?",
                    false
            );
            repository.saveAll(
                    List.of(title, discuss1)
            );
        };
    }

}
