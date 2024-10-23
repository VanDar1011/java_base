package com.base.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    String name;
    @Column(nullable = true)
    String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    Profile profile;
    @Column(name = "roles")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> roles;
//    @Column(length = 20, nullable = false)
//    private  String userName;


    public User(String name, String password, Profile profile, Set<String> roles) {
        this.name = name;
        this.password = password;
        this.profile = profile;
        this.roles = roles;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
