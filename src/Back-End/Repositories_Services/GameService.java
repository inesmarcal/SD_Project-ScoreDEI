//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.projeto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.data.Event;
import com.example.data.Game;

@Service
public class GameService {
    @Autowired    
    private GameRepository gameRepository;
    
    
    public boolean addGame(Game game)  
    {   
        if (game.getTeamA() == game.getTeamB()){
            return false;
        }
        gameRepository.save(game);
        return true;
    }
  
    public void updateGame(int id, String loc, LocalDateTime date){
        Game g = gameRepository.findbyId(id);
        
        Game game = new Game( g.getTeamA(),  g.getTeamB(), loc, date);
        game.setId(id);


        gameRepository.save(game);
        
    }

    public List<Game> getAllGames() 
    {    
        List<Game>gameRecords = new ArrayList<>();    
        gameRepository.findAll().forEach(gameRecords::add);    
        return gameRecords;    
    }
    public List<Game> getAllGamesTeam(String team) 
    {    
        return gameRepository.findbyTeam(team);
    }
  
    public Optional<Game> findById(int id) {
        return gameRepository.findById(id);
    }

    public List<Event> OrderEventOfGame(Game game){
        return this.gameRepository.OrderEventOfGame(game);
    }
}
