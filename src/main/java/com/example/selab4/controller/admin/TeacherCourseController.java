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
    private final TeacherCourseService service;

    @Autowired
    TeacherCourseController(TeacherCourseService teacherCourseService){
        this.service = teacherCourseService;
    }

    // 查看所有的课程申请
    @GetMapping("/getApplication")
    public Response<List<TeacherCourseApplicationVO>> getApplication(){
        return service.showAllApplications();
    }

    @GetMapping("/getPendingApplication")
    public Response<List<TeacherCourseApplicationVO>> getPendingApplication() {
        return service.showPendingApplications();
    }

    // 管理员审核教师的课程申请（如果通过，将完成相应操作）
    @PostMapping("/approve")
    public Response<String> approve(@RequestBody TeacherCourseApplication teacherCourseApplication, @RequestParam(name = "attitude") boolean attitude){
        return service.approve(teacherCourseApplication,attitude);
    }

    // 管理员自己修改课程（传入相对应的课程申请）
    @PostMapping("/modify")
    public Response<String> modify(@RequestBody TeacherCourseApplication teacherCourseApplication){
        return service.modify(teacherCourseApplication);
    }

    @GetMapping("/getTeacherJobNumById")
    public Response<String> getTeacherJobNum(@RequestParam(name = "Id") Integer Id){
        return new Response<>(0,"success", service.getTeacherJobnumById(Id));
    }

    @GetMapping("/getTeacherIdByJobNum")
    public Response<Integer> getTeacherJobNum(@RequestParam(name = "JobNum") String JobNum){
        return new Response<>(0,"success", service.getTeacherIdByJobnum(JobNum));
    }

    // 批量导入课程（csv文件中存的是相对应的课程申请）
    @PostMapping("/file")
    public Response<String> batch_modify(@RequestParam("file") MultipartFile multipartFile){
        return service.BatchModify(multipartFile);
    }

    @GetMapping("/getCourse")
    public Response<List<CourseVO>> getCourse(){
        return service.showAllCourse();
    }
}
