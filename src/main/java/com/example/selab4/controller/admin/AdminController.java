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

    @GetMapping("/valid")
    public Response<String> isValid() {
        return adminService.isValid();
    }

//    @PostMapping("/open")
//    Response<String> open_select_course(){
//        return adminService.openCourseSelect();
//    }

//    @PostMapping("/close")
//    Response<String> close_select_course(){
//        return adminService.closeCourseSelect();
//    }


    @PostMapping("/open/first")
    Response<String> open_first(){
        return adminService.openFirstCourseSelect();
    }

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

    @PostMapping("/begin/second")
    Response<String> begin_second(){
        return adminService.beginSemester();
    }

    @PostMapping("/end/second")
    Response<String> end_second(){
        return adminService.endSemester();
    }
}
