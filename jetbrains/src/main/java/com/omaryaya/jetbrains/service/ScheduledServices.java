package com.omaryaya.jetbrains.service;

import com.omaryaya.jetbrains.entity.Role;
import com.omaryaya.jetbrains.entity.RoleName;
import com.omaryaya.jetbrains.repository.RoleRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledServices {

    @Autowired
    private RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledServices.class);

    @Scheduled(fixedDelayString = "2629800000", initialDelay = 1000)
    public void insertRoles() {
        if(!roleRepository.findByName(RoleName.ROLE_USER).isPresent()) {
            logger.info("Adding Role "+RoleName.ROLE_USER);
            roleRepository.save(new Role(RoleName.ROLE_USER));
        }
        if(!roleRepository.findByName(RoleName.ROLE_ADMIN).isPresent()) {
            logger.info("Adding Role "+RoleName.ROLE_ADMIN);
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        }
    }
    
}
