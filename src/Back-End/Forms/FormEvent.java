//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.formdata;
import com.example.data.Player;
import com.example.data.Event;

public class FormEvent {
    
    private Event  event;
    private Player playerA;
    private Player playerB;
 
    public FormEvent(){
        this.event = new Event();
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Player getPlayerA() {
        return this.playerA;
    }

    public void setPlayerA(Player playerA) {
        this.playerA = playerA;
    }    
    public Player getPlayerB() {
        return this.playerB;
    }

    public void setPlayerB(Player playerB) {
        this.playerB = playerB;
    } 
}
