package com.futbol.Fantasy.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "team"
)
public class Team {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column
    private String name;
    @Column
    private String photo;
    @OneToMany(
            mappedBy = "team",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    private List<Footballer> footballers = new ArrayList();

    public Team() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Footballer> getFootballers() {
        return this.footballers;
    }

    public void setFootballers(List<Footballer> footballers) {
        this.footballers = footballers;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
