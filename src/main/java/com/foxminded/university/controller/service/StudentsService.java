package com.foxminded.university.controller.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.university.controller.repository.GroupRepository;
import com.foxminded.university.controller.repository.StudentRepository;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;

@Service
public class StudentsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    
    @Autowired
    public StudentsService(GroupRepository groupRepository, 
                           StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    public Student getStudent(int studentId) {
        if (logger.isInfoEnabled()) {
            logger.info("Get student by id ({})", studentId);
        }
        return studentRepository.findById(studentId);
    }
    
    public Group getGroupByStudent(int studentId) {
        if (logger.isInfoEnabled()) {
            logger.info("Get group by student id ({})", studentId);
        }
        
        return groupRepository.findByStudentId(studentId);
    }
    
    public Group getGroupById(int groupId) {
        if (logger.isInfoEnabled()) {logger.info("Get group by id ({})", groupId);}
        return groupRepository.findById(groupId);
    }
    
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
    
    public void addStudent(String firstName, String lastName, int groupId) {
        Group group = new Group();
        group.setId(groupId);
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setGroup(group);
        studentRepository.save(student);
    }
    
    public void deleteStudent(int id) {
        Student student = new Student();
        student.setId(id);
        studentRepository.delete(student);
    }
    
    public void update(Student student) {
        studentRepository.save(student);
    }
}
