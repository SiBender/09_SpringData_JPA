package com.foxminded.university.controller.service;

import java.time.format.DateTimeParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.controller.repository.CourseRepository;
import com.foxminded.university.controller.repository.GroupRepository;
import com.foxminded.university.controller.repository.TeacherRepository;
import com.foxminded.university.model.Course;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Teacher;

@Service
public class TeachersService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    
    @Autowired
    public TeachersService(TeacherRepository teacherRepository, 
                           CourseRepository courseRepository, 
                           GroupRepository groupRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.groupRepository = groupRepository;
    }
    
    public Teacher getTeacher(int teacherId) {
        if (logger.isInfoEnabled()) {logger.info("Get teacher by id ({})", teacherId);}
        return teacherRepository.findById(teacherId);
    }
    
    public void createCourse(String name, String description, int teacherId) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("Try to create new course ({}, {}, {})", name, description, teacherId);
            }
            Teacher teacher = new Teacher();
            teacher.setId(teacherId);
            
            Course course = new Course();
            course.setName(name);
            course.setDescription(description);
            course.setTeacher(teacher);
    
            courseRepository.save(course);
            if (logger.isInfoEnabled()) { logger.info("Course created successfully"); }
        } catch (DateTimeParseException ex) {
            if (logger.isErrorEnabled()) { 
                logger.error("Error while creating course", ex);
            }
        }
    }
    
    public List<Course> getCoursesByTeacher(int teacherId) {
        if (logger.isInfoEnabled()) { logger.info("Get courses by teacher id ({})", teacherId); }

        return courseRepository.findByTeacherId(teacherId);
    }
    
    public Course getCourse(int courseId) {
        if (logger.isInfoEnabled()) { logger.info("Get courses by course id ({})", courseId); }
        return courseRepository.findById(courseId);
    }
    
    public void deleteCourse(int id) {
        Course course = new Course();
        course.setId(id);
        courseRepository.delete(course);
    }
    
    public void updateCourse(Course course) {
        courseRepository.save(course);
    }
    
    public List<Teacher> getAll() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers;
    }
    
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
    
    public void deleteGroupsCourse(int groupId, int courseId) {
        groupRepository.deleteGroupsCourse(groupId, courseId);
    }
    
    @Transactional
    public void assignGroupsCourse(int groupId, int courseId) {
        if (logger.isInfoEnabled()) {
            logger.info("Assign group to course ({}, {})", groupId, courseId);
        }
        
        Group group = groupRepository.findById(groupId);        
        Course course = courseRepository.findById(courseId);
        group.getCourses().add(course);
 
        groupRepository.save(group);
    }
    
    public Group getGroupById(int id) {
        return groupRepository.findById(id);
    }
    
    public List<Course> getFreeCourses(Group group) {
        return courseRepository.findFreeCourses(group.getId());
    }
}
