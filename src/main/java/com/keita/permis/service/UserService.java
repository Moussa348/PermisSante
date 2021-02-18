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
      TODO:
        -If age lower than 18 return error with a message
        -check with the Ministry of Health(socialInsurance,isVaccinated) before I instantiate a citizen (by calling WS)
        -Call service that generateQr(sendEmail,pdf...), that I will code in the near future, and associate with User
    */
    public boolean registerUser(User user) {
        try {
            userRepository.save(user);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Only used for testing
    public void registerListUser(List<User> users) {
        try {
            userRepository.saveAll(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    public List<User> getListUser() {
        return userRepository.findAll();
    }

    public boolean deleteUser(Long id) {

        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
