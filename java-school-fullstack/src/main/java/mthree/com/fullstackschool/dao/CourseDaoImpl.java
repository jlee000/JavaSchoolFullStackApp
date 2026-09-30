package mthree.com.fullstackschool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import mthree.com.fullstackschool.dao.mappers.CourseMapper;
import mthree.com.fullstackschool.model.Course;

@Repository
public class CourseDaoImpl implements CourseDao {

    private final JdbcTemplate jdbcTemplate;

    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Course createNewCourse(Course course) {
        //YOUR CODE STARTS HERE
        final String sql = "INSERT INTO course(cid, courseCode, courseDesc, teacherId) VALUES(?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, course.getCourseId());
            statement.setString(2, course.getCourseName());
            statement.setString(3, course.getCourseDesc());
            statement.setInt(4, course.getTeacherId());
            return statement;

        }, keyHolder);

        course.setCourseId(keyHolder.getKey().intValue());

        return course;
        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Course> getAllCourses() {
        //YOUR CODE STARTS HERE
        final String sql = "SELECT cid, courseCode, courseDesc, teacherId FROM course;";
        return jdbcTemplate.query(sql, new CourseMapper());
        //YOUR CODE ENDS HERE
    }

    @Override
    public Course findCourseById(int id) {
        //YOUR CODE STARTS HERE
        final String sql = "SELECT cid, courseCode, courseDesc, teacherId FROM course WHERE cid = ?;";
        return jdbcTemplate.queryForObject(sql, new CourseMapper(), id);
        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateCourse(Course course) {
        //YOUR CODE STARTS HERE
        final String sql = "UPDATE course SET "
                + "courseCode = ?, "
                + "courseDesc = ?, "
                + "teacherId = ? "
                + "WHERE cid = ?;";

        jdbcTemplate.update(sql,
                course.getCourseName(),
                course.getCourseDesc(),
                course.getTeacherId(),
                course.getCourseId());
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteCourse(int id) {
        //YOUR CODE STARTS HERE
        final String sql = "DELETE FROM course WHERE cid = ?;";
        jdbcTemplate.update(sql, id);
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteAllStudentsFromCourse(int courseId) {
        //YOUR CODE STARTS HERE
        final String sql = "DELETE FROM course_student WHERE course_id = ?;";
        jdbcTemplate.update(sql, courseId);
        //YOUR CODE ENDS HERE
    }
}
