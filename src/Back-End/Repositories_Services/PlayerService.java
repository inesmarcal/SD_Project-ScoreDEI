//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.projeto;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.data.Player;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;


    public boolean addPlayer(Player player) {

        Player existing_player = playerRepository.findbyNameAndTeam(player.getName(), player.getTeam().getName());
        if (existing_player == null) {
            playerRepository.save(player);
            return true;
        }
        return false;

    }

    public String BestScorer() {
        if (this.playerRepository.getBestScore().size() > 0) {
            return this.playerRepository.getBestScore().get(0);
        }
        return "-----,-----";
    }

    public List<Player> getAllPlayers() {
        return this.playerRepository.getAllPlayers();
    }

    public int findByName(String name) {
        return this.playerRepository.findByName(name);
    }

    public void changePlayer(String newName, String name, String position, Date birthDate, String newTeam) {
        this.playerRepository.changePlayer(newName, name, position, birthDate);
    }
}
