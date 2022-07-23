//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class User_ {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name,password,address;
    private String email;
    private long contact; 
    private boolean isAdmin;

    public User_(){
        
    }

    public User_(String name, String email, String password, String address, long contact, boolean isAdmin){
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.contact = contact;
        this.isAdmin = isAdmin;
    }

    public boolean getIsAdmin(){
        return isAdmin;
    }
    public String getAddress() {
        return address;
    }

    public long getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setContact(long contact) {
        this.contact = contact;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setIsAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }
    

}
