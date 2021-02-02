package com.foxminded.university.controller.repository;

import org.springframework.data.repository.CrudRepository;

import com.foxminded.university.model.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {    
    public Student findById(int id);
}
