//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.projeto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.data.Event;

@Service
public class EventService {
    @Autowired    
    private EventRepository eventRepository;

    
    
    @Transactional
    public int addEvent(Event event)  {  
        
        List<Event> eventsOrdered = eventRepository.allEventsOrderByDate(event.getGame().getId());
        //nao ha eventos e o primeiro nao e o inicio
        if (eventsOrdered.isEmpty() && !event.getTypeE().equals("Start of the Game")){
            return 1;
        }
        //ja ocorreu o fim do jogo
        if(!eventRepository.findbyTypeE("End of the Game",event.getGame().getId()).isEmpty()){
            return 2;
        }
        int nRed;
        int nYellow;
 
        switch(event.getTypeE()){
            case "Start of the Game":
                List<Event> events = eventRepository.findbyTypeE(event.getTypeE(),event.getGame().getId());
                //ja ha um inicio de jogo
                if (events.size() != 0){
                    return 3;
                }
                break;
           
            case "Goal": 
                nRed = eventRepository.countRedCards(event.getGame().getId(),event.getPlayer().getName());
                nYellow = eventRepository.countYellowCards(event.getGame().getId(),event.getPlayer().getName());
             
                if(nRed > 0 || nYellow > 1){
                    return 4;
                }

            break;

            case "Yellow Card":
                
                nRed = eventRepository.countRedCards(event.getGame().getId(),event.getPlayer().getName());
                nYellow = eventRepository.countYellowCards(event.getGame().getId(),event.getPlayer().getName());
             
                if (nRed > 0 || nYellow > 1){
                    return 4;
                }
                break;

            case "Red Card":

                nRed = eventRepository.countRedCards(event.getGame().getId(),event.getPlayer().getName());
                nYellow = eventRepository.countYellowCards(event.getGame().getId(),event.getPlayer().getName());
             
                if (nRed > 0 || nYellow > 1){
                    return 4;
                }
                break;
                
            
            
        }
        eventRepository.save(event);
        
        return 0;
    }
  
    public List<Event> getAllEvents() 
    {    
        List<Event>allEvents = new ArrayList<>();    
        eventRepository.findAll().forEach(allEvents::add);    
        return allEvents;    
    }

    public List<String> getTypeEvents(){
        List<String> typeEvents = new ArrayList<>();
        typeEvents.add("Start of the Game");
        typeEvents.add("End of the Game");
        typeEvents.add("Goal");
        typeEvents.add("Yellow Card");
        typeEvents.add("Red Card");
        typeEvents.add("Paused Game");
        typeEvents.add("Resumed Game");
        return typeEvents;
    }
  
    public int  getScoreTeam(String team,int gameid){
        return eventRepository.getScorebyTeamAndGame(team, gameid);
    }
  
}
