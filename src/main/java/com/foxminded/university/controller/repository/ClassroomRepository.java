package com.foxminded.university.controller.repository;

import com.foxminded.university.model.*;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClassroomRepository extends PagingAndSortingRepository<Classroom, Integer>  {
    public List<Classroom> findAll(Sort sort);
    
    public Classroom findById(int id);
    
    public Classroom findByNumber(String number);
}
