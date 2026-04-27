package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getAllStudentsWithCourses() {
        List<Student> students = studentRepository.findAll();
        List<StudentCourse> allCourses = studentCourseRepository.findAll();

        Map<Long, List<StudentCourse>> coursesByStudentId = allCourses.stream()
                .collect(Collectors.groupingBy(sc -> sc.getStudent().getId()));

        List<StudentCourse> result = new ArrayList<>();
        for (Student student : students) {
            List<StudentCourse> courses = coursesByStudentId.getOrDefault(student.getId(), Collections.emptyList());
            for (StudentCourse sc : courses) {
                StudentCourse entry = new StudentCourse();
                entry.setStudent(student);
                entry.setCourse(sc.getCourse());
                result.add(entry);
            }
        }
        return result;
    }

    public Optional<Student> findStudentWithHighestGpa() {
        return studentRepository.findStudentWithHighestGpa();
    }

    public String joinStudentNames() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .collect(Collectors.joining(", "));
    }
}

