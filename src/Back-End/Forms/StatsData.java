//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.formdata;


public class StatsData{
    String name;
    int victories;
    int defeats;
    int tied;
    int total;

    public StatsData(){}
    public StatsData(String name, int victories, int defeats, int tied) {
        this.name = name;
        this.victories = victories;
        this.defeats = defeats;
        this.tied = tied;
      
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVictories() {
        return this.victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public int getDefeats() {
        return this.defeats;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }

    public int getTied() {
        return this.tied;
    }

    public void setTied(int tied) {
        this.tied = tied;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


}
