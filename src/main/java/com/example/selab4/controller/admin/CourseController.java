package com.example.selab4.controller.admin;
import com.example.selab4.model.entity.CourseApplication;
import com.example.selab4.model.vo.CourseApplicationVO;
import com.example.selab4.model.vo.CourseVO;
import com.example.selab4.service.admin.CourseService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.selab4.model.entity.Course;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("AdminCourseController")
@RequestMapping("/admin/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    CourseController(CourseService courseService){
        this.courseService=courseService;
    }

    // 查看所有的课程申请
    @GetMapping("/getApplication")
    public Response<List<CourseApplicationVO>> getApplication(){
        return courseService.showAllApplications();
    }

    @GetMapping("/getPendingApplication")
    public Response<List<CourseApplicationVO>> getPendingApplication() {
        return courseService.showPendingApplications();
    }

    // 管理员审核教师的课程申请（如果通过，将完成相应操作）
    @PostMapping("/approve")
    public Response<String> approve(@RequestBody CourseApplication courseApplication,@RequestParam(name = "attitude") boolean attitude){
        return courseService.approve(courseApplication,attitude);
    }

    // 管理员自己修改课程（传入相对应的课程申请）
    @PostMapping("/modify")
    public Response<String> modify(@RequestBody CourseApplication courseApplication){
        return courseService.modify(courseApplication);
    }

    @GetMapping("/getTeacherJobNumById")
    public Response<String> getTeacherJobNum(@RequestParam(name = "Id") Integer Id){
        return new Response<>(0,"success",courseService.getTeacherJobnumById(Id));
    }

    @GetMapping("/getTeacherIdByJobNum")
    public Response<Integer> getTeacherJobNum(@RequestParam(name = "JobNum") String JobNum){
        return new Response<>(0,"success",courseService.getTeacherIdByJobnum(JobNum));
    }

    // 批量导入课程（csv文件中存的是相对应的课程申请）
    @PostMapping("/file")
    public Response<String> batch_modify(@RequestParam("file") MultipartFile multipartFile){
        return courseService.BatchModify(multipartFile);
    }

    @GetMapping("/getCourse")
    public Response<List<CourseVO>> getCourse(){
        return courseService.showAllCourse();
    }
}
