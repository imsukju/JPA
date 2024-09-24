package com.sjjpa10.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID")
    private Long id;

    private String title;

    @Lob
    private String text;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL
    )
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment c)
    {
        c.setPost(this);
        comments.add(c);
    }

    public void removeComment(Comment c)
    {
        c.setPost(null);
        comments.add(c);
    }
}
