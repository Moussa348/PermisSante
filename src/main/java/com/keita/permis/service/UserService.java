package com.keita.permis.service;

import com.keita.permis.model.User;
import com.keita.permis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /*
        TODO
            -Make my controller return a ResponseEntity -> for code status
     */

    public void registerUser(User user) {
        try {
            userRepository.save(user);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void registerListUser(List<User> users) {
        try {
            userRepository.saveAll(users);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public User getUser(Long id){
        return userRepository.getOne(id);
    }

    public List<User> getListUser(){
        return userRepository.findAll();
    }

    public boolean deleteUser(Long id){

        if(userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }



}
