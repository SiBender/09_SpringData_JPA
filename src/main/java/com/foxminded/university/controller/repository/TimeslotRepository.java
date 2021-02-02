package com.foxminded.university.controller.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.foxminded.university.model.Timeslot;

public interface TimeslotRepository extends CrudRepository<Timeslot, Integer> {
    public List<Timeslot> findAll(Sort sort);
    
    public Timeslot findById(int id);
}
