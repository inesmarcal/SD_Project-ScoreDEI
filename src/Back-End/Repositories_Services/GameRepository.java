//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.projeto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

import com.example.data.Game;
import com.example.data.Event;


public interface GameRepository extends CrudRepository<Game, Integer>   
{ 

    @Query("SELECT g FROM Game g WHERE g.id = :id")
    public Game findbyId(@Param("id") int id);

    @Query("SELECT g FROM Game g WHERE g.teamA.name = :team OR g.teamB.name = :team")
    public List<Game> findbyTeam(@Param("team") String team);
    
    //Buscar todos os eventos de um determinado jogo
    @Query("SELECT e FROM Event e WHERE e.game = :game ORDER BY e.date")
    public List<Event> OrderEventOfGame(@Param("game") Game game);
} 