//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.projeto;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.data.Event;

public interface EventRepository extends CrudRepository<Event, Integer> {

    @Query("SELECT e FROM Event e WHERE e.game.id= :id AND e.typeE = :type ")
    public List<Event> findbyTypeE(@Param("type") String type, @Param("id") int id);

    @Query("SELECT e FROM Event e Where e.game.id= :id ORDER BY e.date DESC")
    public List<Event> allEventsOrderByDate(@Param("id") int id);

    @Query("SELECT count(e) FROM Event e Where e.game.id= :id AND  e.typeE = 'Yellow Card' AND ((e.team.name = e.game.teamA.name AND e.player.name = :playerName) OR (e.team.name = e.game.teamB.name AND e.player.name = :playerName))")
    public int countYellowCards(@Param("id") int id, @Param("playerName") String playerName);

    @Query("SELECT count(e) FROM Event e Where e.game.id= :id AND  e.typeE = 'Red Card' AND ((e.team.name = e.game.teamA.name AND e.player.name = :playerName) OR (e.team.name = e.game.teamB.name AND e.player.name = :playerName))")
    public int countRedCards(@Param("id") int id, @Param("playerName") String playerName);

    @Query("SELECT count(e) From Event e where e.game.id = :gameId AND e.typeE = 'Goal' AND e.team.name = :team")
    public int getScorebyTeamAndGame(@Param("team") String team, @Param("gameId") int gameId);

}