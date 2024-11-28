package com.futbol.Fantasy.table.model;

public class FootballerSelectableListView {
    private Long id;
    private String name;
    private String team;

    public FootballerSelectableListView(Long id, String name, String team) {
        this.id = id;
        this.name = name;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return name + " - " + team;
    }
}
