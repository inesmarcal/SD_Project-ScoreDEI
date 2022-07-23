//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.projeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.data.User_;
import com.example.formdata.LoginFormData; 

@Service
public class UserService {
    @Autowired    
    private UserRepository userRepository;

    public boolean addUser(User_ user_)  
    {    
        user_.setPassword(Integer.toString(user_.getPassword().hashCode()));
        User_ existing_User = userRepository.findbyEmail(user_.getEmail());
        if (existing_User == null){
            userRepository.save(user_);  
            return true;
        }
        return false;
     
    }

    //return 0 - authentication failed
    //return 1 - admin permits authentication sucessfull
    //return 2 - authentication sucessfull
    public int authenticate(LoginFormData loginData){
      
       User_ user = userRepository.findbyEmail(loginData.getEmail());
      
       if (user != null && user.getPassword().equals(Integer.toString(loginData.getPassword().hashCode()))){
            if (user.getIsAdmin()){
                return 1;
            }else{
                return 2;
            }
       }
       
    
       return 0;
    }
}
