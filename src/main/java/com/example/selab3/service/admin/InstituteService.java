package com.example.selab3.service.admin;

import com.example.selab3.manager.admin.InstituteManager;
import com.example.selab3.model.entity.Institute;
import com.example.selab3.model.entity.Major;
import com.example.selab3.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service("AdminInstituteService")
public class InstituteService {
    private final InstituteManager manager;

    @Autowired
    public InstituteService(InstituteManager manager) {
        this.manager = manager;
    }

    public Response<Institute> getByName(String instituteName) {
        Institute institute = manager.getInstituteByName(instituteName);
        if (institute == null) {
            return new Response<>(Response.FAIL, "学院: " + instituteName + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "查找学院成功", institute);
    }

    public Response<Institute> getById(Integer instituteId) {
        Institute institute = manager.getInstituteById(instituteId);
        if (institute == null) {
            return new Response<>(Response.FAIL, "学院id: " + instituteId + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "查找学院成功", institute);
    }

    public Response<Institute> create(Institute institute) {
        if (institute.getId() != null) {
            return new Response<>(Response.FAIL, "学院id由系统自动生成", null);
        }

        if (manager.getInstituteByName(institute.getName()) != null) {
            return new Response<>(Response.FAIL, "学院: " + institute.getName() + "已存在", null);
        }

        manager.createInstitute(institute);
        return new Response<>(Response.SUCCESS, "新增学院成功", institute);
    }

    public Response<Institute> delete(String instituteName) {
        Institute institute = manager.getInstituteByName(instituteName);
        if (institute == null) {
            return new Response<>(Response.FAIL, "学院: " + instituteName + "不存在", null);
        }

        // 如果学院还有专业，不允许删除学院
        List<Major> majors = manager.getMajors(institute);
        if (majors.size() > 0) {
            return new Response<>(Response.FAIL, "学院: " + instituteName + "还有专业存在，删除学院失败", null);
        }

        manager.deleteInstitute(institute);
        return new Response<>(Response.SUCCESS, "删除学院成功", institute);
    }

    public Response<Institute> update(Institute institute) {
        if (institute.getId() == null || manager.getInstituteById(institute.getId()) == null) {
            return new Response<>(Response.FAIL, "被修改对象不存在", null);
        }

        Institute oldInstitute = manager.getInstituteById(institute.getId());
        Institute newInstitute = manager.getInstituteByName(institute.getName());
        if (newInstitute != null && !newInstitute.getId().equals(oldInstitute.getId())) {
            return new Response<>(Response.FAIL, "学院: " + institute.getName() + "已存在", null);
        }

        // 级联的update
        manager.updateMajorsByInstitute(oldInstitute, institute);
        manager.updateStudentsByInstitute(oldInstitute, institute);
        manager.updateTeachersByInstitute(oldInstitute, institute);

        oldInstitute.update(institute);
        manager.updateInstitute(oldInstitute);
        return new Response<>(Response.SUCCESS, "修改学院成功", oldInstitute);
    }

    public Response<List<Institute>> getAll() {
        return new Response<>(Response.SUCCESS, "查找所有学院成功", manager.getAllInstitutes());
    }

    public String getNameById(Integer id){
        return manager.getInstituteById(id).getName();
    }


}
