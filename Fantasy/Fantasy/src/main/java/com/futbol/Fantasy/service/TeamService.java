package com.futbol.Fantasy.service;

import com.futbol.Fantasy.model.Team;
import com.futbol.Fantasy.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    @Autowired
    TeamRepository repository;

    public TeamService() {
    }

    public void insert(Team team) {
        this.repository.save(team);
    }

    public void deleteAll() {
        this.repository.deleteAll();
    }
}
