package com.keita.permis.service;

import com.keita.permis.repository.PermitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PoolService {

    @Autowired
    private PermitRepository permitRepository;

    public Integer disableAllPermitExpired(){
        return permitRepository.disablePermit(LocalDate.now());
    }

    public Integer renewPermitExpiredDaily(){
        //GetListCitizen

        //foreach -> call ministry

        /*
            -if nothing do not renew
            -if something --> set Vaccinated from citizen received from ministry
                ---> pass citizen to permitService.renewPermit(Citizen citizen)
                -->done
         */
        return null;
    }

}
