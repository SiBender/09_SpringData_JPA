package com.foxminded.university.controller.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.model.DateInterval;
import com.foxminded.university.model.Timetable;

@Repository
public class TimetableRepository {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    LessonRepository lessonRepository;
    
    @Transactional(readOnly=true)
    public Timetable getByStudent(int studentId, DateInterval dateInterval) {
        if (logger.isDebugEnabled()) {
            logger.debug("Get timetable by student ({}, {}, {})", 
                     studentId, dateInterval.getStartDate(), dateInterval.getEndDate());
        }
        
        Timetable timetable = new Timetable();
        timetable.setDateInterval(dateInterval);
        timetable.setLessons(lessonRepository.getLessonsByStudentAndDate(studentId, 
                                                                  dateInterval.getStartDate(),
                                                                  dateInterval.getEndDate()));
        return timetable;
    }
    
    @Transactional(readOnly=true)
    public Timetable getByTeacher(int teacherId, DateInterval dateInterval) {
        if (logger.isDebugEnabled()) {
            logger.debug("Get timetable by teacher ({}, {}, {})", 
                     teacherId, dateInterval.getStartDate(), dateInterval.getEndDate());
        }
        
        Timetable timetable = new Timetable();
        timetable.setDateInterval(dateInterval);
        timetable.setLessons(lessonRepository.getLessonsByTeacherAndDate(teacherId, 
                                                        dateInterval.getStartDate(),
                                                        dateInterval.getEndDate()));
        return timetable;
    }
}
