package com.home.model.service;

import com.home.model.Passport;
import com.home.model.repository.PassportRepository;
import org.springframework.stereotype.Service;

@Service
public class PassportDAO {
    private PassportRepository passportRepository;

    public PassportDAO(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
        passportRepository.findAll();
    }

    public void update() {
        passportRepository.findAll();
    }

    public void save(Passport passport) {
        passportRepository.save(passport);
    }
}
