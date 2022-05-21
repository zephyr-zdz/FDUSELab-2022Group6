package com.example.selab4.service.admin;

import com.example.selab4.manager.admin.CourseTemplateManager;
import com.example.selab4.model.checker.CourseTemplateChecker;
import com.example.selab4.model.entity.CourseTemplate;
import com.example.selab4.model.entity.Student;
import com.example.selab4.util.Response;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

        if (checker.stillHaveCourse(courseTemplate)) {
            return new Response<>(Response.FAIL, "该课程模板还有课程开设", null);
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

    public Response<String> createByFile(MultipartFile multipartFile) {
        CsvReader csvReader = new CsvReader();
        csvReader.setContainsHeader(true);

        File file = null;
        if (multipartFile != null){
            try {
                file = File.createTempFile("tmp", null);
                multipartFile.transferTo(file);
                System.gc();
                file.deleteOnExit();
            } catch (Exception e){
                return new Response<>(Response.FAIL, "multipartFile转换为File时发生Exception异常", null);
            }
        }

        CsvContainer csv;
        try {
            csv = csvReader.read(file, StandardCharsets.UTF_8);
        } catch(IOException e) {
            return new Response<>(Response.FAIL, "csv文件读取时发生IOException异常", null);
        }

        /* csv文件格式要求(顺序不重要)
            列名应包含：name, coursenum
         */
        StringBuilder result = new StringBuilder("失败序号: ");
        Integer count = 0;
        CourseTemplate courseTemplate = null;
        Response<CourseTemplate> response = null;
        for (CsvRow row : csv.getRows()) {
            count++;
            courseTemplate = new CourseTemplate();
            response = new Response<>();

            courseTemplate.setName(row.getField("name"));
            courseTemplate.setCoursenum(row.getField("coursenum"));
            response = this.create(courseTemplate);
            if (response.getCode().equals(Response.FAIL)) {
                result.append(count.toString() + ",");
            }
        }

        // 只要文件能够读取就是成功
        // 新建失败的情况通过data部分展示
        return new Response<>(Response.SUCCESS, "批量新增课程模板成功", result.toString());
    }
}
