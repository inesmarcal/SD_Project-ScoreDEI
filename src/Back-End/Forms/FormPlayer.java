package com.example.formdata;
import java.sql.Date;

public class FormPlayer {
    
    private String name;
    private String position;
    private Date birthDate;
    private String newName;
    private String newTeam;
 
 
    public FormPlayer(){
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getNewName() {
        return this.newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }    
    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    } 
    public String getNewTeam() {
        return newTeam;
    }

    public void setNewTeam(String newTeam) {
        this.newTeam = newTeam;
    }

}
