package com.futbol.Fantasy.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Entity
@Table(
        name = "player"
)
public class Player {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column
    private String userName;
    @Column
    private String password;


    public Player() {
    }

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }


    public void setPassword(String password) {
        this.password = (new BCryptPasswordEncoder()).encode(password);
    }

    public boolean verifyPassword(String rawPassword) {
        return (new BCryptPasswordEncoder()).matches(rawPassword, this.password);
    }
}
