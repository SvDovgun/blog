package com.luxoft.blog.post.dto;

import lombok.Data;

@Data
public class DefaultPostDto {

    private Long id;

    private String title;

    private String content;

    private boolean star;
}
