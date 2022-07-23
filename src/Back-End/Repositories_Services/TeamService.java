//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.projeto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.data.Game;
import com.example.data.Player;
import com.example.data.Team;
import com.example.formdata.StatsData;
import com.example.formdata.TeamForm;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

@Service
public class TeamService {
    @Autowired    
    private TeamRepository teamRepository;
    
    @Autowired
    private PlayerService playerService;
    
    @Autowired
    private EventService eventService;
    
    @Autowired
    private GameService gameService;
    
    
    public void updateTeam(String newName, int id){
        Optional<Team> t = teamRepository.findById(id);
        if (t.isPresent()){
    
            Team team = new Team(newName, t.get().getUrlImage());
            team.setId(id);
            team.setPlayers(t.get().getPlayers());
        
            teamRepository.save(team);
        }
    }

    public void generateTeam(String teamName) {
        String apiKey = "c9044a6ae3fdfc7fa981bc4ba117a2c4";

        Team existing_team = teamRepository.findbyName(teamName);
        if (existing_team == null) {

            HttpResponse<JsonNode> jsonResponse = Unirest.get("https://v3.football.api-sports.io/teams")
                    .queryString("name", teamName)
                    .header("x-rapidapi-host", "v3.football.api-sports.io")
                    .header("x-rapidapi-key", apiKey)
                    .asJson();

            JSONObject jsonObjectTeam = jsonResponse.getBody().getObject();
            int numResult = jsonObjectTeam.getInt("results");

            if (numResult != 1)
                return;

            JSONObject jsonteam = jsonObjectTeam.getJSONArray("response").getJSONObject(0).getJSONObject("team");
            Team team = new Team(teamName, jsonteam.getString("logo"));
            teamRepository.save(team);

            jsonResponse = Unirest.get("https://v3.football.api-sports.io/players")
                    .queryString("season", 2021)
                    .queryString("team", jsonteam.getInt("id"))
                    .header("x-rapidapi-host", "v3.football.api-sports.io")
                    .header("x-rapidapi-key", apiKey).asJson();
            JSONArray jsonObjectPlayers = jsonResponse.getBody().getObject().getJSONArray("response");

            for (int i = 0; i < jsonObjectPlayers.length(); i++) {
                JSONObject obj = jsonObjectPlayers.getJSONObject(i);
                JSONObject player = obj.getJSONObject("player");
                JSONObject stats = obj.getJSONArray("statistics").getJSONObject(0);
                Date birthDate = Date.valueOf(player.getJSONObject("birth").getString("date"));
                playerService.addPlayer(new Player(player.getString("name"), team,
                        stats.getJSONObject("games").getString("position"), birthDate));
            }
        }
    }
  
    public boolean addTeam(TeamForm teamForm) {
        System.out.println(teamForm.getImageUrl());
        Team team = teamRepository.findbyName(teamForm.getName());
      
        if (team == null){
            teamRepository.save(new Team(teamForm.getName(), teamForm.getImageUrl()));
            return true;
        }
        return false;
    
    }
    
    public List<Team> getAllTeams()  
    {    
        List<Team>teamRecords = new ArrayList<>();    
        teamRepository.findAll().forEach(teamRecords::add);    
        return teamRecords;    
    }

 

    public Team findByName(String name){
            return this.teamRepository.findbyName(name);
    }


    public StatsData getStats(String team){
        StatsData stats = new StatsData();
        int teamScore, adversaryScore;
        int nVictorie  = 0, nDefeat = 0, nTied = 0;
        List<Game> allGamesTeam = gameService.getAllGamesTeam(team);
        stats.setTotal(allGamesTeam.size()); 
        stats.setName(team);
        for (Game g : allGamesTeam) {
            if (g.getTeamA().getName().equals(team)){
                teamScore = eventService.getScoreTeam(team, g.getId());
                adversaryScore = eventService.getScoreTeam(g.getTeamB().getName(), g.getId());
            }else{
                teamScore = eventService.getScoreTeam(team, g.getId());
                adversaryScore = eventService.getScoreTeam(g.getTeamA().getName(), g.getId());
            }
            if (teamScore > adversaryScore){
                nVictorie++;
            }else{ 
                if (teamScore < adversaryScore){
                    nDefeat++;
                }else{
                    nTied++;
                }

            }
        }
        stats.setDefeats(nDefeat);
        stats.setTied(nTied);
        stats.setVictories(nVictorie);
        
        return stats; 
    }
}
