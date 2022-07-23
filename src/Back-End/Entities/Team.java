//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name, urlImage;

    @OneToMany(mappedBy="team")
    private List<Player> players;

 

    public Team(){}

  
    public Team(String name, String urlImage){
        this.name = name;
        this.urlImage = urlImage;
        this.players = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        
    }
    public String getUrlImage(){
        return urlImage;
    }

    public void setUrlImage(String image){
        this.urlImage = image;
    }

    public List<Player> getPlayers(){
        return players;
    }

    public void setPlayers(List<Player> players){
        this.players = players;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }
    
}