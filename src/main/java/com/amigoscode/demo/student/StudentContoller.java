package com.amigoscode.demo.student;

import com.amigoscode.demo.Exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("students")
public class StudentContoller {

    private final StudentService studentService;

    @Autowired
    public StudentContoller(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping
    public List<Student> getAllStudents(){
        //throw new ApiRequestException("oops cannot get all students With CUSTOM Exception");

        //throw new IllegalStateException("oops cannot get all students");

        return studentService.getAllStudents();
    }

    @PostMapping
    public void addNewStudent(@RequestBody @Valid Student student)
    {

        studentService.addNewStudent(student);
    }

    @GetMapping(path = "{id}/courses")
    public List<StudentCourse> getAllCourse(@PathVariable ("id") UUID studentId)
    {

        return studentService.getAllCourse(studentId);
    }
}
