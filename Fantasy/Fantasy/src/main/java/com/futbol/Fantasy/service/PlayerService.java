package com.futbol.Fantasy.service;


import com.futbol.Fantasy.model.Player;
import com.futbol.Fantasy.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository repository;


    public void save(Player player) {
        repository.save(player);
    }

    @Transactional
    public List<Player> getAllPlayers() {
        return repository.findAll();
    }

    public Player getPlayer(String userName, String password) {
        Optional<Player> player = repository.findPlayerByUserNameIgnoreCase(userName);

        if(player.isPresent()) {
            return player.get().verifyPassword(password) ? player.get() : null;
        }

        return null;
    }

    public Player findById(Long id){
        return repository.getReferenceById(id);
    }

    public Optional<Player> findByUserName(String userName) {
        return repository.findPlayerByUserNameIgnoreCase(userName);
    }

    public boolean existsByUsername(String userName) {
        return repository.findPlayerByUserNameIgnoreCase(userName).isPresent();
    }
}