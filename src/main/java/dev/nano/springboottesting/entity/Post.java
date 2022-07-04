package dev.nano.springboottesting.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "posts")
@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class Post {

    @Id
    @SequenceGenerator(name = "user_sequence",sequenceName = "user_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_sequence")
    @Column(name = "id",nullable = false)
    private long id;


    @NotNull(message="title is required")
    @Column(nullable=false)
    private String title;

    @NotNull(message="description is required")
    @Column(nullable=false)
    @Lob
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    /* UserId of the user who created this post */
    private Integer userId;

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
    }
}
