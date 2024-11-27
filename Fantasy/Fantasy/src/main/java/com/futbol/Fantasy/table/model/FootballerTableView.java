package com.futbol.Fantasy.table.model;

import com.futbol.Fantasy.model.Footballer;
import javafx.scene.image.ImageView;

public class FootballerTableView {

    private Long id;

    private ImageView photo;

    private String name;

    private String rol;

    private String team;

    private int points;
    private String style;
    private Footballer footballer;

    public FootballerTableView(ImageView photo, String name) {
        this.photo = photo;
        this.name = name;
    }

    public FootballerTableView(ImageView photo, String name, String rol, String team) {
        this.photo = photo;
        this.name = name;
        this.rol = rol;
        this.team = team;
    }

    public FootballerTableView(ImageView photo, String name, String rol) {
        this.photo = photo;
        this.name = name;
        this.rol = rol;
    }

    public FootballerTableView(int points, String name) {
        this.points = points;
        this.name = name;
    }


    public FootballerTableView(Long id, String name, String team) {
        this.name = name;
        this.id = id;
        this.team = team;
    }

    public FootballerTableView(ImageView photo, String name, String rol, Footballer footballer) {
        this.photo = photo;
        this.name = name;
        this.rol = rol;
        this.footballer = footballer;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public Footballer getFootballer() {
        return footballer;
    }

    public void setFootballer(Footballer footballer) {
        this.footballer = footballer;
    }
}
