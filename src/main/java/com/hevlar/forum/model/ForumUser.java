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
@Data
@Entity
@Table(name = "FORUM_USER")
public class ForumUser {
    @Id
    @NonNull
    @Column(name = "USER_ID")
    String userId;

    @NonNull
    @Column(name = "GIVEN_NAME")
    String givenName;

    @NonNull
    @Column(name = "FAMILY_NAME")
    String familyName;

    @NonNull
    @Column(name = "EMAIL", unique = true)
    String email;

    @NonNull
    @Column(name = "PASSWORD")
    String password;

    @NonNull
    @Column(name = "ENABLED")
    Boolean enabled;

    @NonNull
    @Column(name = "LOCKED")
    Boolean locked;

    @CreationTimestamp
    @Column(name = "CREATED", nullable = false, updatable = false)
    LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "UPDATED", nullable = false)
    LocalDateTime updated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_ROLES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID"))
    Collection<ForumRole> roles;

    public ForumUser(@NonNull String userId, @NonNull String givenName, @NonNull String familyName, @NonNull String email, @NonNull String password){
        this.userId = userId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.locked = false;
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }
}
