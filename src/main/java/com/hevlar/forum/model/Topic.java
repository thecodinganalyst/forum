package com.hevlar.forum.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "TOPIC")
public class Topic {
    @Id
    @GeneratedValue
    @Column(name = "TOPIC_ID")
    Long topicId;

    @Column(name = "TITLE", unique = true, nullable = false)
    String title;

    @CreationTimestamp
    @Column(name = "CREATED", nullable = false)
    LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "UPDATED", nullable = false)
    LocalDateTime updated;

    @JsonManagedReference
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Post> posts;
}
