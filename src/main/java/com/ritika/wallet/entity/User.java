package com.ritika.wallet.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    @Column(name = "password_hash")
    private String password;

    @Builder.Default
    private String role = "USER";

    @Builder.Default
    private String kycStatus = "PENDING"; // NEW FIELD

    public User(String name, String email, String phone, String password, String role, String kycStatus) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.kycStatus = kycStatus;
    }


}
