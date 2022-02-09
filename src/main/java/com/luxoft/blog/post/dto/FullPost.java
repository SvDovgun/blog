package com.luxoft.blog.post.dto;

import lombok.Data;

import java.util.List;

@Data
public class FullPost {
    private Long id;

    private String title;

    private String content;

    private boolean star;

    private List<DefaultCommentDto> comments;
}
