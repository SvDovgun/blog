package com.luxoft.blog.post.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //{CascadeType.MERGE, CascadeType.PERSIST})
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinTable(name = "post_tags",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id")  })
    private Set<Tag> tags  = new HashSet<>();

    public Set<Tag> getTags(){
        return tags;
    }

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
        Set<String> tagNames = new HashSet<>();
        for (Tag tag : tags) {
            tagNames.add(tag.getName());
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return star == post.star && id.equals(post.id) && title.equals(post.title) && content.equals(post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, star);
    }
}
