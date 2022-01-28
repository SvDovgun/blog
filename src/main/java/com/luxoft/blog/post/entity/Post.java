package com.luxoft.blog.post.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "posts")
public class Post {
    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )
    private Long id;
    @Column
    @NotBlank(message = "Please add Title")
    private String title;
    @Column
    private String content;
    @Column(columnDefinition = "boolean default false")
    private boolean star;

    public Post() {
    }

    public Post(Long id, String title, String content, boolean star) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.star=star;
    }

    public Post(String title, String content, boolean star) {
        this.title = title;
        this.content = content;
        this.star=star;
    }
}
