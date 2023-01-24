package com.hevlar.forum.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
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
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "topicId", scope = Topic.class)
    @ManyToOne
    @JoinColumn(name = "TOPIC_ID")
    private Topic topic;
}
