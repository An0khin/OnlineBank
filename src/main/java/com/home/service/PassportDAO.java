package com.home.service;

import com.home.model.Passport;
import com.home.dao.PassportRepository;
import org.springframework.stereotype.Service;

@Service
public class PassportDAO {
    private final PassportRepository passportRepository;

    public PassportDAO(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
        passportRepository.findAll();
    }

    public void save(Passport passport) {
        passportRepository.save(passport);
    }

    public Passport findByNameAndSurname(String name, String surname) {
        return passportRepository.findByNameAndSurname(name, surname).orElse(null);
    }

    public void update(Integer id, Passport passport) {
        passportRepository.updateNameAndSurnameAndDateBirthAndSeriesAndNumberById(
                passport.getName(),
                passport.getSurname(),
                passport.getDateBirth(),
                passport.getSeries(),
                passport.getNumber(),
                id);
    }
}
