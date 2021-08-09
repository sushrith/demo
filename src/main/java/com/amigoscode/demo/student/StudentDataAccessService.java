package com.amigoscode.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Repository
public class StudentDataAccessService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Student> selectALlStudents(){
        String sql=""+"SELECT student_id,first_name,last_name,email,gender FROM student";

        return jdbcTemplate.query(sql, mapStudentFromDb());
        
    }
    int insertStudent(UUID StudentId, Student student) {

        String sql=""+"INSERT INTO student (student_id,first_name,last_name,email,gender) " +
                "VALUES(?, ?, ?, ?, ?::gender)";
        return jdbcTemplate.update(sql,StudentId,student.getFirstName(),student.getLastName(),student.getEmail(),student.getGender().name().toUpperCase());

    }
    @SuppressWarnings("ConstantConditions")
    boolean isEmailTaken(String email)
    {
        String sql=""+"SELECT EXISTS(SELECT 1 FROM student WHERE email=?)";
        boolean b= jdbcTemplate.queryForObject(
                sql,
                new Object[] {email},
                (resultSet,i)-> resultSet.getBoolean(1)
        );
        //System.out.println(b);
        return b;

    }

    private RowMapper<Student> mapStudentFromDb() {
        return (resultSet, i) -> {
            String studentIdStr = resultSet.getString("student_id");
            UUID studentId = UUID.fromString(studentIdStr);
            String first_name = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");
            String email = resultSet.getString("email");
            String genderStr = resultSet.getString("gender").toUpperCase();
            Student.Gender gender = Student.Gender.valueOf(genderStr);
            return new Student(studentId, first_name, last_name, email, gender);
        };
    }


    public List<StudentCourse> getAllCourses(UUID studentId) {
        String sql=""+"SELECT student_id,course_id,start_date,end_date,grade,name,description,department,teacher_name FROM student JOIN student_course USING (student_id) JOIN course USING (course_id) WHERE student_id=?";

        return jdbcTemplate.query(sql,new Object[]{studentId},mapStudentCourseFromDb());
    }

    private RowMapper<StudentCourse> mapStudentCourseFromDb() {
        return (resultSet, i) -> {
            String studentIdStr = resultSet.getString("student_id");
            UUID studentId = UUID.fromString(studentIdStr);
            String courseIdStr = resultSet.getString("course_id");
            UUID courseId = UUID.fromString(courseIdStr);
            String startDatestr=resultSet.getString("start_date");
            LocalDate startDate=LocalDate.parse(startDatestr);
            String endDatestr=resultSet.getString("end_date");
            LocalDate endDate=LocalDate.parse(endDatestr);
            String gradeStr=resultSet.getString("grade");
            Integer grade= Optional.ofNullable(resultSet.getString("grade")).map(Integer::parseInt).orElse(null);
            String name=resultSet.getString("name");
            String description=resultSet.getString("description");
            String department=resultSet.getString("department");
            String teachersName=resultSet.getString("teacher_name");
            return new StudentCourse(studentId,courseId,startDate,endDate,grade,name,description,department,teachersName);
        };


    }
}
