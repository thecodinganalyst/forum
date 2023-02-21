package com.hevlar.forum.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "FORUM_ROLE")
public class ForumRole {

    @Id
    @NonNull
    @Column(name = "ROLE_ID")
    String roleId;

    @NonNull
    @Column(name = "ROLE_NAME", unique = true)
    String name;

    @NonNull
    @Column(name = "VALID")
    Boolean valid;

    @CreationTimestamp
    @Column(name = "CREATED", nullable = false, updatable = false)
    LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "UPDATED", nullable = false)
    LocalDateTime updated;

    @ManyToMany(mappedBy = "roles")
    Collection<ForumUser> users;
}
