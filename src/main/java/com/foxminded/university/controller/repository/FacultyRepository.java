package com.foxminded.university.controller.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.foxminded.university.model.Faculty;

public interface FacultyRepository extends CrudRepository<Faculty, Integer> {
    public Faculty findById(int id);
    
    public List<Faculty> findAll(Sort sort);
}
