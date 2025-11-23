package com.premierLeague.premier_Zone.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name="user_info",
indexes = {
        @Index(columnList = "userId"),
        @Index(columnList = "username"),
        @Index(columnList = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    private LocalDateTime date;

    @ElementCollection
    @CollectionTable(name="user_followed_team",joinColumns = @JoinColumn(name = "userId"))
    @Column(name="team_name")
    private Set<String>followedTeam = new HashSet<>();

}
