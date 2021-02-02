package com.foxminded.university.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.foxminded.university.model.Course;

public interface CourseRepository extends CrudRepository<Course, Integer> {
    public Course findById(int id);
    
    public List<Course> findByTeacherId(int id);

    @Query("SELECT c FROM Course c WHERE c NOT IN "
         + "(SELECT c FROM Course c JOIN c.groups g WHERE g.id = ?1 ) "
         + "ORDER BY c.id")
    public List<Course> findFreeCourses(int groupId);
}
