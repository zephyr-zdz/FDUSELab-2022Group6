package com.example.selab4.service.admin;

import com.example.selab4.manager.admin.CourseTemplateManager;
import com.example.selab4.model.checker.CourseTemplateChecker;
import com.example.selab4.model.entity.CourseTemplate;
import com.example.selab4.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AdminCourseTemplateService")
public class CourseTemplateService {
    private final CourseTemplateManager manager;
    private final CourseTemplateChecker checker;

    @Autowired
    public CourseTemplateService(CourseTemplateManager manager, CourseTemplateChecker checker) {
        this.manager = manager;
        this.checker = checker;
    }

    public Response<List<CourseTemplate>> getAll() {
        return new Response<>(Response.SUCCESS, "查找所有课程模板成功", manager.getAllCourseTemplates());
    }

    public Response<CourseTemplate> delete(CourseTemplate courseTemplate) {
        if (!checker.exist(courseTemplate)) {
            return new Response<>(Response.FAIL, "课程模板不存在", null);
        }

        manager.deleteCourseTemplate(courseTemplate);
        return new Response<>(Response.SUCCESS, "课程模板删除成功", courseTemplate);
    }

    public Response<CourseTemplate> create(CourseTemplate courseTemplate) {
        if (checker.haveId(courseTemplate)) {
            return new Response<>(Response.FAIL, "课程模板id由系统自动生成", null);
        }

        if (checker.existCoursenum(courseTemplate)) {
            return new Response<>(Response.FAIL, "课程模板的编号：" + courseTemplate.getCoursenum() + "已存在", null);
        }

        if (checker.existName(courseTemplate)) {
            return new Response<>(Response.FAIL, "课程模板的名字：" + courseTemplate.getName() + "已存在", null);
        }

        manager.createCourseTemplate(courseTemplate);
        return new Response<>(Response.SUCCESS, "课程模板新增成功", courseTemplate);
        // TODO 最后返回的创建对象，id是否符合预期？
    }

    public Response<CourseTemplate> update(CourseTemplate courseTemplate) {
        if (!checker.existId(courseTemplate)) {
            return new Response<>(Response.FAIL, "课程模板不存在", null);
        }

        CourseTemplate oldCourseTemplate = manager.getCourseTemplateById(courseTemplate.getId());

        if (checker.existOtherCoursenum(courseTemplate, oldCourseTemplate)) {
            return new Response<>(Response.FAIL, "课程模板的编号：" + courseTemplate.getCoursenum() + "已存在", null);
        }

        if (checker.existOtherName(courseTemplate, oldCourseTemplate)) {
            return new Response<>(Response.FAIL, "课程模板的名字：" + courseTemplate.getName() + "已存在", null);
        }

        oldCourseTemplate.update(courseTemplate);
        manager.updateCourseTemplate(oldCourseTemplate);
        return new Response<>(Response.SUCCESS, "修改课程模板成功", oldCourseTemplate);
    }
}
