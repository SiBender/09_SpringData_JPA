package com.foxminded.university.controller.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

import com.foxminded.university.controller.repository.CourseRepository;
import com.foxminded.university.controller.repository.TeacherRepository;
import com.foxminded.university.model.Course;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeachersServiceTest {
    @Mock
    CourseRepository courseRepository;
    @Mock
    TeacherRepository teacherRepository;
    
    @InjectMocks
    TeachersService teachersService;
    
    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    void getTeacherShouldCallTeacherRepositoryTest() {
        teachersService.getTeacher(111);
        verify(teacherRepository).findById(111);
    }

    @Test
    void createCourseShouldCallCourseRepositoryTest() {
        teachersService.createCourse("CourseName", "CourseDescription", 222);
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void getCoursesByTeacherShouldCallCourseRepositoryTest() {
        teachersService.getCoursesByTeacher(333);
        verify(courseRepository).findByTeacherId(anyInt());
    }

    @Test
    void getCourseShouldCallCourseRepositoryTest() {
        teachersService.getCourse(4444);
        verify(courseRepository).findById(4444);
    }
}
