package com.foxminded.university.controller.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

import com.foxminded.university.controller.repository.ClassroomRepository;
import com.foxminded.university.controller.repository.CourseRepository;
import com.foxminded.university.controller.repository.LessonRepository;
import com.foxminded.university.controller.repository.TimeslotRepository;
import com.foxminded.university.model.Lesson;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LessonServiceTest {
    @Mock
    LessonRepository lessonRepository;
    @Mock
    TimeslotRepository timeslotRepository;
    @Mock
    ClassroomRepository classroomRepository;
    @Mock
    CourseRepository courseRepository;
    
    @InjectMocks
    LessonService lessonService;
    
    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    void getAllClassroomsShouldCallClassroomRepositoryTest() {
        lessonService.getAllClassrooms();
        verify(classroomRepository).findAll(any(Sort.class));
    }

    @Test
    void getAllTimeslotsShouldCallTimeslotRepositoryTest() {
        lessonService.getAllTimeslots();
        verify(timeslotRepository).findAll(any(Sort.class));
    }

    @Test
    void getCoursesByTeacherShouldCallCourseRepositoryTest() {
        lessonService.getCoursesByTeacher(123);
        verify(courseRepository).findByTeacherId(anyInt());
    }

    @Test
    void createLessonShouldCallLessonRepositoryTest() {
        lessonService.createLesson("2020-01-31", 333, 444, 555);
        verify(lessonRepository).save(any(Lesson.class));
    }

}
