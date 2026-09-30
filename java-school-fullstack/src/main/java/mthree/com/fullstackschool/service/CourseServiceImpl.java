package mthree.com.fullstackschool.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import mthree.com.fullstackschool.dao.CourseDao;
import mthree.com.fullstackschool.model.Course;

@Service
public class CourseServiceImpl implements CourseServiceInterface {

    //YOUR CODE STARTS HERE
    private final CourseDao courseDao;

    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }
    //YOUR CODE ENDS HERE

    public List<Course> getAllCourses() {
        //YOUR CODE STARTS HERE

        return courseDao.getAllCourses();

        //YOUR CODE ENDS HERE
    }

    public Course getCourseById(int id) {
        //YOUR CODE STARTS HERE
        try{
            return courseDao.findCourseById(id);
        }catch(DataAccessException e){
            Course course = new Course();
            course.setCourseName("Course Not Found");
            course.setCourseDesc("Course Not Found");
            return course;            
        }
        //YOUR CODE ENDS HERE
    }

    public Course addNewCourse(Course course) {
        //YOUR CODE STARTS HERE
        boolean invalid = false;
        if (course.getCourseName() == null || course.getCourseName().isBlank()) {
            course.setCourseName("Name blank, course NOT added");
            invalid = true;
        }

        if (course.getCourseDesc() == null || course.getCourseDesc().isBlank()) {
            course.setCourseDesc("Description blank, course NOT added");
            invalid = true;
        }

        if (invalid) {
            return course;
        }

        courseDao.createNewCourse(course);
        return course;
        //YOUR CODE ENDS HERE
    }

    public Course updateCourseData(int id, Course course) {
        //YOUR CODE STARTS HERE
        if (id != course.getCourseId()) {
            course.setCourseName("IDs do not match, course not updated");
            course.setCourseDesc("IDs do not match, course not updated");
            return course;
        }

        courseDao.updateCourse(course);
        return course;
        //YOUR CODE ENDS HERE
    }

    public void deleteCourseById(int id) {
        //YOUR CODE STARTS HERE

        courseDao.deleteCourse(id);
        System.out.println("Course ID: " + id + " deleted");
        
        //YOUR CODE ENDS HERE
    }
}
