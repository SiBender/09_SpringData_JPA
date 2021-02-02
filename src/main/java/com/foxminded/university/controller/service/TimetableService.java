package com.foxminded.university.controller.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.university.controller.repository.TimetableRepository;
import com.foxminded.university.controller.util.DateIntervalGenerator;
import com.foxminded.university.model.DateInterval;
import com.foxminded.university.model.Timetable;

@Service
public class TimetableService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private final TimetableRepository timetableRepository;
    private final DateIntervalGenerator dateIntervalGenerator;
    
    @Autowired
    public TimetableService(TimetableRepository timetableRepository, DateIntervalGenerator dateIntervalGenerator) {
        this.timetableRepository = timetableRepository;
        this.dateIntervalGenerator = dateIntervalGenerator;
    }
    
    public Timetable getTeacherTimetable(String startDate, String endDate, int teacherId) {
        if (logger.isInfoEnabled()) {
            logger.info("Get timetable for teacher ({}, {}, {})", startDate, endDate, teacherId);
        }
        
        DateInterval dateInterval = generateDateInterval(startDate, endDate);
        
        return timetableRepository.getByTeacher(teacherId, dateInterval);
    }

    public Timetable getStudentTimetable(String startDate, String endDate, int studentId) {
        if (logger.isInfoEnabled()) {
            logger.info("Get timetable for student ({}, {}, {})", startDate, endDate, studentId);
        }        
        DateInterval dateInterval = generateDateInterval(startDate, endDate);
        
        return timetableRepository.getByStudent(studentId, dateInterval);
    }
    
    private DateInterval generateDateInterval(String startDate, String endDate) {
        DateInterval dateInterval = dateIntervalGenerator.getFromString(startDate, endDate);
        if (dateInterval == null) {
            dateInterval = dateIntervalGenerator.getCurrentWeek();
        }
        
        return dateInterval;
    }
}
