//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.projeto;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.data.Player;

public interface PlayerRepository extends CrudRepository<Player, Integer>   
{ 
    @Query("SELECT p FROM Player p WHERE p.name = :name AND p.team.name = :team")
    public Player findbyNameAndTeam(@Param("name") String name,@Param("team") String team);
    
    //Permite ir buscar a lista dos marcadores por ordem decrescente de golos marcados
    //Escrever que vai buscar o primeiro elemento na classe gameService
    @Query("SELECT p.name, count(e)" +
    " FROM Event e" +
    " JOIN Player p" +
    " ON p.id = e.player" +
    " WHERE e.typeE = 'Goal'" +  
    " GROUP BY p.name" +
    " ORDER BY count(e) DESC"  )
    public List<String> getBestScore();

    //Vai buscar todos os jogadores
    @Query("SELECT p FROM Player p")
    public List<Player> getAllPlayers();
    
    //Encontrar quantos jogadores tem este nome
    @Query("SELECT count(p) FROM Player p WHERE p.name=:name")
    public int findByName(@Param("name") String name);
    
    //Muda um jogador com um determinado nome
    @Transactional
    @Modifying
    @Query("UPDATE Player p SET name=?1,  position=?3, birthDate=?4 WHERE p.name=?2")
    public void changePlayer(String newName, String name, String position, Date birthDate);
} 