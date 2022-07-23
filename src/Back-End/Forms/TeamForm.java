//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.formdata;

public class TeamForm {
    private String name;
    private String imageUrl;
    private int id;

    public TeamForm() {
    }


    public TeamForm(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
 

    public String isImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getImageUrl() {
        return this.imageUrl;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
