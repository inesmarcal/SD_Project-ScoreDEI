//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.projeto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import com.example.data.User_;

public interface UserRepository extends CrudRepository<User_, Integer>   
{ 

    @Query("SELECT u FROM User_ u WHERE u.email = :email")
    public User_ findbyEmail(@Param("email") String email);
    
} 