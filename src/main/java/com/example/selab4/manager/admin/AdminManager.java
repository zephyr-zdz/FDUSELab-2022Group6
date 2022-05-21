package com.example.selab4.manager.admin;

import com.example.selab4.mapper.*;
import com.example.selab4.model.entity.Administrator;
import com.example.selab4.model.entity.Course;
import com.example.selab4.model.entity.StuCourse;
import com.example.selab4.model.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Integer.parseInt;

@Component("AdminAdminManager")
public class AdminManager {
    private final AdminMapper adminMapper;
    private final StuCourseMapper stuCourseMapper;
    private final CourseMapper  courseMapper;
    private final StudentMapper studentMapper;
    private final ScheduleMapper scheduleMapper;

    @Autowired
    AdminManager(AdminMapper adminMapper, StuCourseMapper stuCourseMapper, CourseMapper courseMapper, StudentMapper studentMapper, ScheduleMapper scheduleMapper){
        this.adminMapper=adminMapper;
        this.stuCourseMapper = stuCourseMapper;
        this.courseMapper = courseMapper;
        this.studentMapper = studentMapper;
        this.scheduleMapper = scheduleMapper;
    }

    public void saveAdmin(Administrator administrator){
        adminMapper.save(administrator);
    }

    public Administrator findAdmin(){
        return adminMapper.findAdministratorById(1);
    }

    //  第一轮选课结束瞬间，管理员根据优先级退选超出容量的学生
    public void solveOverflow_single(Integer courseid) {
        Student student;
        List<String> gradeList = new ArrayList<>();
        List<StuCourse> stuCourseList = stuCourseMapper.findStuCoursesByCourseid(courseid);
        for (StuCourse stuCourse : stuCourseList){
            student=studentMapper.findStudentById(stuCourse.getStudentid());
            gradeList.add(student.getStunum());
        }

        Collections.shuffle(gradeList);
        gradeList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return parseInt(o1.substring(0, 2)) - parseInt(o2.substring(0, 2));
            }
        });

        String capacity = courseMapper.findCourseById(courseid).getCapacity();
        int capacity_Integer = parseInt(capacity);
        if (capacity_Integer >= gradeList.size()) {return;}
        List<String> to_be_deleted = gradeList.subList(capacity_Integer,gradeList.size());
        for(String stunum : to_be_deleted){
            Integer studentid = studentMapper.findStudentByStunum(stunum).getId();
            stuCourseMapper.deleteByStudentidAndCourseid(studentid,courseid);
            Course course = courseMapper.findCourseById(courseid);
            course.reduceStudent();
        }
    }

    public void solveOverflow() {
        List<Course> courseList = courseMapper.findCourseBySemester(adminMapper.findAdministratorById(1).getSemester());
        for (Course course : courseList){
            solveOverflow_single(course.getId());
        }
    }

    public void changeS2F() {
        List<StuCourse> stuCourseList = stuCourseMapper.findAllByStatus("S");
        for (StuCourse stuCourse : stuCourseList) {
            stuCourse.setStatus("F");
            stuCourseMapper.save(stuCourse);
        }
    }

    public void clearSchedule() {
        scheduleMapper.deleteAll();
    }
}
