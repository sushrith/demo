package com.amigoscode.demo.student;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class StudentCourse {
    private final UUID studentId;
    private final UUID CourseId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Integer grade;
    private final String name;
    private final String description;
    private final String department;
    private final String teachersName;

    public StudentCourse(UUID studentId, UUID courseId, LocalDate startDate, LocalDate endDate, Integer grade, String name, String description, String department, String teachersName) {
        this.studentId = studentId;
        CourseId = courseId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.grade = grade;
        this.name = name;
        this.description = description;
        this.department = department;
        this.teachersName = teachersName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDepartment() {
        return department;
    }

    public String getTeachersName() {
        return teachersName;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public UUID getCourseId() {
        return CourseId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getGrade() {
        return grade;
    }
}
