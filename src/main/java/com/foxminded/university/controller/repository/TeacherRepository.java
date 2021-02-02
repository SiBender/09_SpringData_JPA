package com.foxminded.university.controller.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.foxminded.university.model.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Integer> {
    public Teacher findById(int id);
    
    public List<Teacher> findAll();
    
    public List<Teacher> findByFacultyId(int facultyId);
}
