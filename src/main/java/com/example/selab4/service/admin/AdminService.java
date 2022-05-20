package com.example.selab4.service.admin;

import com.example.selab4.manager.admin.AdminManager;
import com.example.selab4.model.entity.Administrator;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service("AdminAdminService")
public class AdminService {
    private final AdminManager adminManager;

    @Autowired
    AdminService(AdminManager adminManager){
        this.adminManager=adminManager;
    }

    public Response<String> openCourseSelect(){
        Administrator administrator=adminManager.findAdmin();
        administrator.setSelectcoursefunction("on");
        adminManager.saveAdmin(administrator);
        return new Response<>(Response.SUCCESS,"success","on");
    }

    public Response<String> closeCourseSelect(){
        Administrator administrator=adminManager.findAdmin();
        administrator.setSelectcoursefunction("off");
        adminManager.saveAdmin(administrator);
        return new Response<>(Response.SUCCESS,"success","off");
    }


    public Response<String> currentState() {
        Administrator administrator = adminManager.findAdmin();
        String msg;
        String state;
        if (administrator.getSemesterbegin().equals("off")) {
            msg = "学期未开始";
            state = "off";
        } else if (administrator.getChoosecourse1().equals("on")) {
            msg = "第一轮选课已开始";
            state = "first";
        } else if (administrator.getChoosecourse2().equals("on")) {
            msg = "第二轮选课已开始";
            state = "second";
        } else {
            msg = "学期已开始，选课未开始";
            state = "off";
        }
        return new Response<>(Response.SUCCESS,msg, state);
    }

    public Response<String> openFirstCourseSelect() {
        Administrator administrator = adminManager.findAdmin();
        if(administrator.getSemesterbegin().equals("off"))
            return new Response<>(Response.FAIL,"学期尚未开始",null);
        if (administrator.getChoosecourse1().equals("on")) {
            return new Response<>(Response.FAIL, "第一轮选课开关已经开启", null);
        }
        if (administrator.getChoosecourse2().equals("on")) {
            return new Response<>(Response.FAIL, "第二轮选课开关已经开启", null);
        }

        // 前置条件：学期已经开始，1、2轮选课开关均为off
        administrator.setChoosecourse1("on");
        administrator.setSelectcoursefunction("on");
        adminManager.saveAdmin(administrator);

        return new Response<>(Response.SUCCESS,"第一轮选课开关成功开启",null);
    }

    public Response<String> closeFirstCourseSelect() {
        Administrator administrator = adminManager.findAdmin();
        if (administrator.getChoosecourse1().equals("off")) {
            return new Response<>(Response.FAIL, "第一轮选课开关尚未开启", null);
        }

        // 前置条件：1轮开
        administrator.setChoosecourse1("off");
        administrator.setSelectcoursefunction("off");
        adminManager.saveAdmin(administrator);

        // 调用第一轮结束时应执行的功能
        adminManager.solveOverflow();
        return new Response<>(Response.SUCCESS, "第一轮选课开关成功关闭", null);
    }

    public Response<String> openSecondCourseSelect() {
        Administrator administrator = adminManager.findAdmin();
        if(administrator.getSemesterbegin().equals("off"))
            return new Response<>(Response.FAIL,"学期尚未开始",null);
        if (administrator.getChoosecourse1().equals("on")) {
            return new Response<>(Response.FAIL, "第一轮选课开关已经开启", null);
        }
        if (administrator.getChoosecourse2().equals("on")) {
            return new Response<>(Response.FAIL, "第二轮选课开关已经开启", null);
        }

        // 前置条件：学期已经开始，1、2轮选课开关均为off
        administrator.setChoosecourse2("on");
        administrator.setSelectcoursefunction("on");
        adminManager.saveAdmin(administrator);

        return new Response<>(Response.SUCCESS,"第二轮选课开关成功开启",null);
    }

    public Response<String> closeSecondCourseSelect() {
        Administrator administrator = adminManager.findAdmin();
        if (administrator.getChoosecourse2().equals("off")) {
            return new Response<>(Response.FAIL, "第二轮选课开关尚未开启", null);
        }

        // 前置条件：2轮开
        administrator.setChoosecourse2("off");
        administrator.setSelectcoursefunction("off");
        adminManager.saveAdmin(administrator);

        return new Response<>(Response.SUCCESS, "第一轮选课开关成功关闭", null);
    }

    public Response<String> beginSemester() {
        Administrator administrator = adminManager.findAdmin();
        if (administrator.getSemesterbegin().equals("on")) {
            return new Response<>(Response.FAIL, "学期已经开始", null);
        }

        // 前置条件：学期未开始
        administrator.setSemesterbegin("on");
        adminManager.saveAdmin(administrator);

        return new Response<>(Response.SUCCESS, "开始学期成功", null);
    }

    public Response<String> endSemester() {
        Administrator administrator = adminManager.findAdmin();
        if (administrator.getSemesterbegin().equals("off")) {
            return new Response<>(Response.FAIL, "学期已经结束", null);
        }
        if (administrator.getChoosecourse1().equals("on")) {
            return new Response<>(Response.FAIL, "第一轮选课开关尚未关闭", null);
        }
        if (administrator.getChoosecourse2().equals("on")) {
            return new Response<>(Response.FAIL, "第二轮选课开关尚未关闭", null);
        }

        // 前置条件：学期已经开始，且1、2轮开关均为off
        administrator.setSemesterbegin("off");
        adminManager.saveAdmin(administrator);

        return new Response<>(Response.SUCCESS, "结束学期成功", null);
    }
}
