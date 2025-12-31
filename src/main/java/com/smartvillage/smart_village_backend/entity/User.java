package com.smartvillage.smart_village_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 10)
    private String mobile;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String firebaseUid;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getMobile() { return mobile; }
    public String getEmail() { return email; }
    public String getFirebaseUid() { return firebaseUid; }
    public Role getRole() { return role; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setEmail(String email) { this.email = email; }
    public void setFirebaseUid(String firebaseUid) { this.firebaseUid = firebaseUid; }
    public void setRole(Role role) { this.role = role; }
    private LocalDateTime createdAt;

}
