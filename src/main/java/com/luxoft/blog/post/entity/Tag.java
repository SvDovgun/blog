package com.luxoft.blog.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "tags")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_generator")
    private Long id;

    @NotBlank (message = "Tag name is mandatory (max length is to 20)")
    @Column ( unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "tags")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<Post> posts =  new HashSet<>();;

    public Tag(String name) {
        this.name = name;
    }

    public Tag(String name, Set<Post> posts) {
        this.name = name;
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public String toString() {
        Set<String> postNames = new HashSet<>();
        for (Post post : posts) {
            postNames.add(post.getTitle());
        }

        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", posts=" + posts +
                '}';
    }
}
