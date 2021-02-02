package com.foxminded.university.controller.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.controller.repository.ClassroomRepository;
import com.foxminded.university.controller.repository.CourseRepository;
import com.foxminded.university.controller.repository.FacultyRepository;
import com.foxminded.university.controller.repository.GroupRepository;
import com.foxminded.university.controller.repository.StudentRepository;
import com.foxminded.university.controller.repository.TeacherRepository;
import com.foxminded.university.model.Classroom;
import com.foxminded.university.model.Course;
import com.foxminded.university.model.Faculty;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;
import com.foxminded.university.model.Teacher;

@Service
public class AdministrativeService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private final FacultyRepository facultyRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;
    private final CourseRepository courseRepository;
    
    @Autowired
    public AdministrativeService(FacultyRepository facultyRepository, GroupRepository groupRepository, 
               StudentRepository studentRepository, TeacherRepository teacherRepository, 
               ClassroomRepository classroomRepository, CourseRepository courseRepository) {
        this.facultyRepository = facultyRepository;
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.classroomRepository = classroomRepository;
        this.courseRepository = courseRepository;
    }
    
    public void createFaculty(String shortName, String fullName) {
        Faculty faculty = new Faculty();
        faculty.setShortName(shortName);
        faculty.setFullName(fullName);
        facultyRepository.save(faculty);
        if (logger.isInfoEnabled()) {
            logger.info("Create faculty ({}, {})", shortName, fullName);
        }
    }
    
    public void deleteFaculty(int id) {
        Faculty faculty = new Faculty();
        faculty.setId(id);
        facultyRepository.delete(faculty);
    }
    
    public Faculty getFacultyById(int id) {
        return facultyRepository.findById(id);
    }
    
    public void updateFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
    }
    
    public Group getGroupById(int id) {
        return groupRepository.findById(id);
    }
    
    public void createGroup(String name, int facultyId) {
        Group group = new Group();
        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        
        group.setGroupName(name);
        group.setFaculty(faculty);
        groupRepository.save(group);
        if (logger.isInfoEnabled()) {
            logger.info("Create group ({}, {})", name, facultyId);
        }
    }
    
    public void deleteGroupById(int id) {
        Group group = new Group();
        group.setId(id);
        groupRepository.delete(group);
    }
    
    public void updateGroup(Group group) {
        groupRepository.save(group);
    }
    
    public void createTeacher(String firstName, String lastName, int facultyId) {
        Teacher teacher = new Teacher();
        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        
        teacher.setFaculty(faculty);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacherRepository.save(teacher);
        if (logger.isInfoEnabled()) {
            logger.info("Create teacher ({}, {}, {})", firstName, lastName, facultyId);
        }
    }
    
    public void deleteTeacher(int id) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacherRepository.delete(teacher);
    }
    
    public void updateTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }
    
    public Teacher getTeacherById(int id) {
        return teacherRepository.findById(id);
    }
    
    public void createStudent(String firstName, String lastName, int groupId) {
        Group group = new Group();
        group.setId(groupId);
        
        Student student = new Student();
        student.setGroup(group);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        studentRepository.save(student);
        if (logger.isInfoEnabled()) {
            logger.info("Create student ({}, {}, {})", firstName, lastName, groupId);
        }
    }
    
    @Transactional
    public void assignGroupToCourse(int groupId, int courseId) {
        if (logger.isInfoEnabled()) {
            logger.info("Assign group to course ({}, {})", groupId, courseId);
        }
        
        Group group = groupRepository.findById(groupId);        
        Course course = courseRepository.findById(courseId);
        group.getCourses().add(course);
 
        groupRepository.save(group);
    }
    
    public void createClassroom(String number, int capacity) {
        Classroom classroom = new Classroom();
        classroom.setNumber(number);
        classroom.setCapacity(capacity);
        classroomRepository.save(classroom);
        if (logger.isInfoEnabled()) {
            logger.info("Create classroom ({}, {})", number, capacity);
        }
    }
    
    public List<Faculty> getAllFaculties() {
        if (logger.isInfoEnabled()) {
            logger.info("Get all faculties");
        }
        return facultyRepository.findAll(Sort.by("id"));
    }
    
    public List<Group> getGroupsByFaculty(int facultyId) {
        if (logger.isInfoEnabled()) {
            logger.info("Get groups by faculty ({})", facultyId);
        }

        return groupRepository.findByFacultyId(facultyId);
    }
    
    public List<Teacher> getTeachersByFaculty(int facultyId) {
        if (logger.isInfoEnabled()) {
            logger.info("Get teachers by faculty ({})", facultyId);
        }
        
        return teacherRepository.findByFacultyId(facultyId);
    }
    
    public List<Course> getCourses(int teacherId) {
        if (logger.isInfoEnabled()) {
            logger.info("Get courses by teacher ({})", teacherId);
        }
        
        return courseRepository.findByTeacherId(teacherId);
    }
}
