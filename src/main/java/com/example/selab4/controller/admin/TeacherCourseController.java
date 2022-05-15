package com.example.selab4.controller.admin;
import com.example.selab4.model.entity.TeacherCourseApplication;
import com.example.selab4.model.vo.TeacherCourseApplicationVO;
import com.example.selab4.model.vo.CourseVO;
import com.example.selab4.service.admin.TeacherCourseService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("AdminTeacherCourseController")
@RequestMapping("/admin/teachercourse")
public class TeacherCourseController {
    private final TeacherCourseService teacherCourseService;

    @Autowired
    TeacherCourseController(TeacherCourseService teacherCourseService){
        this.teacherCourseService = teacherCourseService;
    }

    // 查看所有的课程申请
    @GetMapping("/getApplication")
    public Response<List<TeacherCourseApplicationVO>> getApplication(){
        return teacherCourseService.showAllApplications();
    }

    @GetMapping("/getPendingApplication")
    public Response<List<TeacherCourseApplicationVO>> getPendingApplication() {
        return teacherCourseService.showPendingApplications();
    }

    // 管理员审核教师的课程申请（如果通过，将完成相应操作）
    @PostMapping("/approve")
    public Response<String> approve(@RequestBody TeacherCourseApplication teacherCourseApplication, @RequestParam(name = "attitude") boolean attitude){
        return teacherCourseService.approve(teacherCourseApplication,attitude);
    }

    // 管理员自己修改课程（传入相对应的课程申请）
    @PostMapping("/modify")
    public Response<String> modify(@RequestBody TeacherCourseApplication teacherCourseApplication){
        return teacherCourseService.modify(teacherCourseApplication);
    }

    @GetMapping("/getTeacherJobNumById")
    public Response<String> getTeacherJobNum(@RequestParam(name = "Id") Integer Id){
        return new Response<>(0,"success", teacherCourseService.getTeacherJobnumById(Id));
    }

    @GetMapping("/getTeacherIdByJobNum")
    public Response<Integer> getTeacherJobNum(@RequestParam(name = "JobNum") String JobNum){
        return new Response<>(0,"success", teacherCourseService.getTeacherIdByJobnum(JobNum));
    }

    // 批量导入课程（csv文件中存的是相对应的课程申请）
    @PostMapping("/file")
    public Response<String> batch_modify(@RequestParam("file") MultipartFile multipartFile){
        return teacherCourseService.BatchModify(multipartFile);
    }

    @GetMapping("/getCourse")
    public Response<List<CourseVO>> getCourse(){
        return teacherCourseService.showAllCourse();
    }
}
