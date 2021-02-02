package com.foxminded.university.controller.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.foxminded.university.model.Lesson;

public interface LessonRepository extends CrudRepository<Lesson, Integer> {
    public Lesson findById(int id);

    @Query("SELECT l FROM Lesson l "
            + "JOIN FETCH l.course c "
            + "JOIN c.groups g "
            + "JOIN g.students s "
            + "WHERE s.id = ?1 "
            + "AND (l.date BETWEEN ?2 AND ?3)")
    public List<Lesson> getLessonsByStudentAndDate(int studentId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT l FROM Lesson l "
            + "JOIN FETCH l.course c "
            + "JOIN c.teacher t "
            + "WHERE t.id = ?1 "
            + "AND (l.date BETWEEN ?2 AND ?3)")
    public List<Lesson> getLessonsByTeacherAndDate(int teacherId, LocalDate startDate, LocalDate endDate);
    
    
}
