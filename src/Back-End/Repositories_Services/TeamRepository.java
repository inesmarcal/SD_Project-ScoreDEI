//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.projeto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.data.Team;

public interface TeamRepository extends CrudRepository<Team, Integer>   
{ 

    @Query("SELECT t FROM Team t WHERE t.name = :name")
    public Team findbyName(@Param("name") String name);


    @Query("SELECT t FROM Team t WHERE t.id = :id")
    public Team findById(@Param("id") String id);

    

} 