package com.foxminded.university.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.university.model.Group;

@Repository
public interface GroupRepository extends CrudRepository<Group, Integer> {
    public List<Group> findAll();
    
    public List<Group> findByFacultyId(int facultyId);
    
    public Group findById(int id);

    @Query("SELECT g FROM Group g JOIN g.students s WHERE s.id = ?1")
    public Group findByStudentId(int studentId);

    @Query(value = "DELETE from groups_courses WHERE group_id = ?1 AND course_id = ?2",
           nativeQuery = true)
    public void deleteGroupsCourse(int groupId, int courseId);
}
