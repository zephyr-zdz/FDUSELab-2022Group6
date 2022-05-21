package com.example.selab4.controller.admin;

import com.example.selab4.service.admin.AdminService;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("AdminAdminController")
@RequestMapping("/admin/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    AdminController(AdminService adminService){
        this.adminService=adminService;
    }

    @GetMapping("/semester/state")
    public Response<String> semesterState(){return adminService.getSemesterState();}
    @GetMapping("/semester")
    public Response<String> semester(){return adminService.getSemester();}

    @GetMapping("/valid")
    public Response<String> currentState() {
        return adminService.currentState();
    }

    @PostMapping("/open/first")
    Response<String> open_first(){
        return adminService.openFirstCourseSelect();
    }

    // 第一轮结束：按优先级踢人
    @PostMapping("/close/first")
    Response<String> close_first(){
        return adminService.closeFirstCourseSelect();
    }

    @PostMapping("/open/second")
    Response<String> open_second(){
        return adminService.openSecondCourseSelect();
    }

    @PostMapping("/close/second")
    Response<String> close_second(){
        return adminService.closeSecondCourseSelect();
    }

    @PostMapping("/semester/begin")
    Response<String> begin_semester(){
        return adminService.beginSemester();
    }

    // TODO 学期结束：已选=>已修。
    @PostMapping("/semester/end")
    Response<String> end_semester(){
        return adminService.endSemester();
    }
}
