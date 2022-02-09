package com.luxoft.blog.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DefaultCommentDto {
    private Long commentId;

    private String text;

    private LocalDateTime creationDate = LocalDateTime.now();
}
