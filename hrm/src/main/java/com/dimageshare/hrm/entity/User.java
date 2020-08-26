package com.dimageshare.hrm.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", length = 45, nullable = false)
    private String username;
    @Column(name = "password", length = 45, nullable = false)
    private String password;
    @Column(name = "email", length = 45, nullable = false)
    private String email;
    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;
    @Column(name = "profile_image_url", length = 45)
    private String profileImageUrl;
    @Column(name = "is_active")
    private Short isActive;
    @Column(name = "is_lock")
    private Short isLock;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
}
