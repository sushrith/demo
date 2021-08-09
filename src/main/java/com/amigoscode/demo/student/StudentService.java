package com.amigoscode.demo.student;

import com.amigoscode.demo.Exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {


    private final StudentDataAccessService studentDataAccessService;
    private final EmailValidation emailValidation;

    @Autowired
    public StudentService(StudentDataAccessService studentDataAccessService, EmailValidation emailValidation) {
        this.studentDataAccessService = studentDataAccessService;
        this.emailValidation = emailValidation;
    }

    public List<Student> getAllStudents(){
        return studentDataAccessService.selectALlStudents();
    }

    public void addNewStudent(Student student) {
       addNewStudent(null,student);
    }
    public void addNewStudent(UUID studentId,Student student) {
        UUID newStudentId=Optional.ofNullable(studentId).orElse(UUID.randomUUID());
        if(!emailValidation.test(student.getEmail())) {
            throw new ApiRequestException(student.getEmail() +"Email is Not Valid");
        }

        if(studentDataAccessService.isEmailTaken(student.getEmail()))
        {
            throw new ApiRequestException(student.getEmail()+" is Taken..!!");
        }
        studentDataAccessService.insertStudent(newStudentId,student);
    }

    public List<StudentCourse> getAllCourse(UUID studentId) {
        return studentDataAccessService.getAllCourses(studentId);
    }
}
