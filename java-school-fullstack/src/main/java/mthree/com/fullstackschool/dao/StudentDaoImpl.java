package mthree.com.fullstackschool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mthree.com.fullstackschool.dao.mappers.StudentMapper;
import mthree.com.fullstackschool.model.Student;

@Repository
public class StudentDaoImpl implements StudentDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Student createNewStudent(Student student) {
        //YOUR CODE STARTS HERE
        final String sql = "INSERT INTO student(sid, fName, lName) VALUES(?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, student.getStudentId());
            statement.setString(2, student.getStudentFirstName());
            statement.setString(3, student.getStudentLastName());
            return statement;

        }, keyHolder);

        student.setStudentId(keyHolder.getKey().intValue());

        return student;
        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE
        final String sql = "SELECT sid, fName, lName FROM student;";
        return jdbcTemplate.query(sql, new StudentMapper());
        //YOUR CODE ENDS HERE
    }

    @Override
    public Student findStudentById(int id) {
        //YOUR CODE STARTS HERE
        final String sql = "SELECT sid, fName, lName FROM student WHERE sid = ?;";
        return jdbcTemplate.queryForObject(sql, new StudentMapper(), id);
        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateStudent(Student student) {
        //YOUR CODE STARTS HERE
        final String sql = "UPDATE student SET "
                + "fName = ?, "
                + "lName = ? "
                + "WHERE sid = ?;";

        jdbcTemplate.update(sql,
                student.getStudentFirstName(),
                student.getStudentLastName(),
                student.getStudentId());
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteStudent(int id) {
        //YOUR CODE STARTS HERE
        final String sql = "DELETE FROM student WHERE sid = ?;";
        jdbcTemplate.update(sql, id);
        //YOUR CODE ENDS HERE
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE
        final String sql = "INSERT INTO course_student (student_id, course_id) VALUES(?,?);";
        jdbcTemplate.update(sql, studentId, courseId);
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE
        final String sql = "DELETE FROM course_student WHERE course_id = ? AND student_id = ?;";
        jdbcTemplate.update(sql, courseId, studentId);
        //YOUR CODE ENDS HERE
    }
}
