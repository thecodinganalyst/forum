package com.hevlar.forum.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Data
@Table(name = "POST")
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    Long postId;

    @NonNull
    @Column(name = "TEXT", nullable = false)
    String text;

    @CreationTimestamp
    @Column(name = "CREATED", nullable = false)
    LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "UPDATED", nullable = false)
    LocalDateTime updated;

    @NonNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOPIC_ID")
    private Topic topic;
}
