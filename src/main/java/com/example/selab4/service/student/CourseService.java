package com.example.selab4.service.student;

import com.example.selab4.manager.student.CourseManager;
import com.example.selab4.model.entity.*;
import com.example.selab4.model.vo.CourseVO;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StudentCourseService")
public class CourseService {
    private final CourseManager manager;

    @Autowired
    public CourseService(CourseManager manager) {
        this.manager = manager;
    }

    public Response<List<CourseVO>> getAllByMajor(String studentMajor) {
        // 判定是否在选课期内
        String select_course_function = manager.findAdmin().getSelectcoursefunction();
        if(select_course_function.equals("off"))
            return new Response<>(Response.FAIL, "当前不是选课期", null);

        Major major = manager.findMajorByName(studentMajor);
        if (major == null) {
            return new Response<>(Response.FAIL, "专业: " + studentMajor + "不存在", null);
        }
        return new Response<>(Response.SUCCESS, "根据专业查找课程成功", manager.findCoursesByMajor(major));
    }

    public Response<String> isValid() {
        Administrator administrator = manager.findAdmin();
        return new Response<>(Response.SUCCESS,"success", administrator.getSelectcoursefunction());
    }

    public Response<List<CourseVO>> getAllByStunum(String stunum) {
        Student student = manager.findStudentByStunum(stunum);
        if (student == null) {
            return new Response<>(Response.FAIL, "学号: " + stunum + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "根据学号查找课程成功", manager.findCourseVOsByStudent(student));
    }

    public Response<String> choose(Integer studentid, Integer courseid) {
        Student student = manager.findStudentByStudentid(studentid);
        Administrator administrator = manager.findAdmin();
        if (student == null) {
            return new Response<>(Response.FAIL, "学生id: " + studentid + "不存在", null);
        }

        Course course = manager.findCourseByCourseid(courseid);
        if (course == null) {
            return new Response<>(Response.FAIL, "课程id: " + courseid + "不存在", null);
        }

        // 1、处于选课期
        if (!manager.chooseCourseFunctionIsOn(administrator)) {
            return new Response<>(Response.FAIL, "不在选课期", null);
        }

        // 2、课程人数上限,考虑第一轮选课时不设置选课上限
        if (!manager.checkCapacity(course, administrator)) {
            return new Response<>(Response.FAIL, "课程id: " + courseid + "的课程人数已满", null);
        }

        // 3、学生的时间日程是否冲突
        if (!manager.checkSchedule(student, course)) {
            return new Response<>(Response.FAIL, "学生id: " + studentid + "的学生上课时间发生冲突", null);
        }

        // 4、学生已选/已修相同课程模板
        if(manager.courseTemplateConflict(student,course)){
            return new Response<>(Response.FAIL, "学生id: " + studentid + "的学生已经修过该课程", null);
        }

        manager.choose(student, course);
        return new Response<>(Response.SUCCESS, "学生选课成功", null);
    }

    public Response<String> delete(Integer studentid, Integer courseid) {
        Student student = manager.findStudentByStudentid(studentid);
        if (student == null) {
            return new Response<>(Response.FAIL, "学生id: " + studentid + "不存在", null);
        }

        Course course = manager.findCourseByCourseid(courseid);
        if (course == null) {
            return new Response<>(Response.FAIL, "课程id: " + courseid + "不存在", null);
        }

        // 1、处于选课期
        Administrator administrator = manager.findAdmin();
        if (!manager.chooseCourseFunctionIsOn(administrator)) {
            return new Response<>(Response.FAIL, "不在选课期", null);
        }

        // 2、学生是否已选该课程
        if (!manager.checkStuCourse(student, course)) {
            return new Response<>(Response.FAIL, "学号: " + student.getStunum() + "的学生没有选过课程id: " + courseid + "的课程", null);
        }

        manager.delete(student, course);
        return new Response<>(Response.SUCCESS, "学生退课成功", null);
    }

    public Response<List<CourseVO>> getSelectedByStunum(String stunum) {
        Student student = manager.findStudentByStunum(stunum);
        if (student == null) {
            return new Response<>(Response.FAIL, "学号: " + stunum + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "根据学号查找课程成功", manager.findCoursesByStudentAndStatus(student,"S"));
    }

    public Response<List<CourseVO>> getFinishedByStunum(String stunum) {
        Student student = manager.findStudentByStunum(stunum);
        if (student == null) {
            return new Response<>(Response.FAIL, "学号: " + stunum + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "根据学号查找课程成功", manager.findCoursesByStudentAndStatus(student,"F"));
    }
}
