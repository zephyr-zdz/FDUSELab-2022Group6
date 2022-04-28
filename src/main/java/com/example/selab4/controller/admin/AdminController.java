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

    @GetMapping("/isValid")
    public Response<String> isValid() {
        return adminService.isValid();
    }

    @PostMapping("/open")
    Response<String> open_select_course(){
        return adminService.openCourseSelect();
    }

    @PostMapping("/close")
    Response<String> close_select_course(){
        return adminService.closeCourseSelect();
    }
}
