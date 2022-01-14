package com.luxoft.blog.post;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    public List<Post> getPost() {
        System.out.println("in blog method");
        return List.of(new Post(
                1L,
                "title line",
                "many many text of content"
        ));
    }
}
