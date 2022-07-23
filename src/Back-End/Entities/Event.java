//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.data;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;

@XmlRootElement
@Entity
public class Event implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Game game;

    @ManyToOne
    private Player player;
    @ManyToOne
    private Team team;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)        
    private LocalDateTime date;

    private String typeE;



    public Event() {
    }

    public Event(Game game, String typeE, Player player, Team team, LocalDateTime date) {
    
        this.game = game;
        this.typeE = typeE;
        this.player = player;
        this.team = team;
        this.date = date;
    }



    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getTypeE() {
        return this.typeE;
    }

    public void setTypeE(String typeE) {
        this.typeE = typeE;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
 

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
}
