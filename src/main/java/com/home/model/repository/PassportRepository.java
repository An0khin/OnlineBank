package com.home.model.repository;

import com.home.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {
    @Transactional
    @Modifying
    @Query("""
            update Passport p set p.name = ?1, p.surname = ?2, p.dateBirth = ?3, p.series = ?4, p.number = ?5
            where p.id = ?6""")
    int updateNameAndSurnameAndDateBirthAndSeriesAndNumberById(String name, String surname, Date dateBirth, Integer series, Integer number, Integer id);

}
