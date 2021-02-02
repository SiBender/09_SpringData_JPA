package com.foxminded.university.controller.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.model.Faculty;
import com.foxminded.university.model.Group;

@Sql(scripts = "classpath:testDatabase.sql")
@SpringBootTest
class GroupRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    GroupRepository groupRepository;
 
    @Test
    void addShouldCreateNewRowInGroupsTableTest() {
        Group group = new Group();
        group.setGroupName("testGroup");
        Faculty faculty = new Faculty();
        faculty.setId(1);
        group.setFaculty(faculty);
        
        groupRepository.save(group);
        
        int actual = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM groups", Integer.class);
        assertEquals(2, actual);
    }

    @Test
    void getAllShouldReturnListOfGroups() {
        List<Group> actualGroups = groupRepository.findAll();
        assertEquals(1, actualGroups.size());
        assertEquals("cs-20", actualGroups.get(0).getGroupName());
    }

    @Test
    void getByFacultyShouldReturnEmtyListForNonExistingDataTest() {
        List<Group> actual = groupRepository.findByFacultyId(100);
        assertTrue(actual.isEmpty());
    }

    @Test
    void getByFacultyShouldReturnListOfGroupsTest() {
        List<Group> actual = groupRepository.findByFacultyId(1);
        assertEquals(1, actual.size());
        assertEquals("cs-20", actual.get(0).getGroupName());
    }

    @Test
    void getByIdShouldReturnGroupObjectTest() {
        Group actual = groupRepository.findById(1);
        assertNotNull(actual);
        assertNotNull(actual.getCourses());
        assertNotNull(actual.getStudents());
    }

    @ParameterizedTest
    @CsvSource({"100", "200", "-1000"})
    void getByIdShouldReturnNullForNonExistingDataTest(int id) {
        assertNull(groupRepository.findById(id));
    }

    @Test
    @Transactional(readOnly=true)
    void getByStudentShouldReturnGroupObjectTest() {
        Group actual = groupRepository.findByStudentId(1);
        assertEquals(1, actual.getId());
        assertEquals("cs-20", actual.getGroupName());
        assertEquals("CS", actual.getFaculty().getShortName());
    }

    @ParameterizedTest
    @CsvSource({"1000", "555500", "-1000"})
    void getByStudentReturnNullForNonExistingDataTest(int id) {
            assertNull(groupRepository.findByStudentId(id));
    }
}
