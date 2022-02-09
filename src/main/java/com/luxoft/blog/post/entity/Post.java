package com.luxoft.blog.post.entity;

import lombok.*;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Comment> comments;

    public Post(String title, String content, boolean star) {
        this.title = title;
        this.content = content;
        this.star=star;
    }

    public Post(Long id, String title, String content, boolean star) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.star = star;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", star=" + star +
                '}';
    }

    public boolean getStar() {
        return this.star;
    }

    public boolean isStar() {
        return this.star;
    }
}
