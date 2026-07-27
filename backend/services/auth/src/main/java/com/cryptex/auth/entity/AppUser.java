package com.cryptex.auth.entity;

import com.cryptex.auth.enums.Role;
import com.cryptex.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

//import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Builder @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Role role = Role.USER;

    @Column(nullable = false)
    @Builder.Default // Default: FALSE
    private boolean enabled = false;

//    @Column(nullable = false, updatable = false)
//    private Instant createdAt;
//
//    @Column(nullable = false)
//    private Instant updatedAt;

    public void enable(){
        enabled = true;
    }

    public void disable(){
        this.enabled = false;
    }

    public void changePassword(String password){
        this.password = password;
    }
}
