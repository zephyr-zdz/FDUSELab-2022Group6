package com.example.selab4.service.admin;

import com.example.selab4.manager.admin.AdminManager;
import com.example.selab4.model.entity.Administrator;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AdminAdminService")
public class AdminService {
    private final AdminManager adminManager;

    @Autowired
    AdminService(AdminManager adminManager){
        this.adminManager=adminManager;
    }

    public Response<String> openCourseSelect(){
        Administrator administrator=adminManager.findAdminById(1);
        administrator.setSelectcoursefunction("on");
        adminManager.saveAdmin(administrator);
        return new Response<>(Response.SUCCESS,"success","on");
    }

    public Response<String> closeCourseSelect(){
        Administrator administrator=adminManager.findAdminById(1);
        administrator.setSelectcoursefunction("off");
        adminManager.saveAdmin(administrator);
        return new Response<>(Response.SUCCESS,"success","off");
    }


    public Response<String> isValid() {
        Administrator administrator=adminManager.findAdminById(1);
        return new Response<>(Response.SUCCESS,"success",administrator.getSelectcoursefunction());
    }
}
