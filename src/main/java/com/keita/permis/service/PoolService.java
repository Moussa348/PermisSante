package com.keita.permis.service;

import com.keita.permis.repository.PermitRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PoolService {

    @Autowired
    private PermitRepository permitRepository;



}
