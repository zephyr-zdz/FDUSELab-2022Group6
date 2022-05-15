package com.example.selab4.service.admin;

import com.example.selab4.manager.admin.TeacherManager;
import com.example.selab4.model.entity.Course;
import com.example.selab4.model.entity.TeacherCourseApplication;
import com.example.selab4.model.entity.Teacher;
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

@Service("AdminTeacherService")
public class TeacherService {
    private final TeacherManager manager;

    @Autowired
    public TeacherService(TeacherManager manager) {
        this.manager = manager;
    }

    public Response<List<Teacher>> getAll() {
        return new Response<>(Response.SUCCESS, "查找所有教师成功", manager.getAllTeachers());
    }

    public Response<Teacher> getById(Integer teacherId) {
        Teacher teacher = manager.getTeacherById(teacherId);
        if (teacher == null) {
            return new Response<>(Response.FAIL, "教师id: " + teacherId + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "查找教师成功", teacher);
    }

    public Response<Teacher> getByJobnum(String jobnum) {
        Teacher teacher = manager.getTeacherByJobnum(jobnum);
        if (teacher == null) {
            return new Response<>(Response.FAIL, "工号: " + jobnum + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "查找教师成功", teacher);
    }

    public Response<Teacher> create(Teacher teacher) {
        if (teacher.getId() != null) {
            return new Response<>(Response.FAIL, "教师id由系统自动生成", null);
        }

        if (manager.getTeacherByIdentity(teacher.getIdentity()) != null || manager.getStudentByIdentity(teacher.getIdentity()) != null) {
            return new Response<>(Response.FAIL, "身份证号: " + teacher.getIdentity() + "已存在", null);
        }

        if (manager.getTeacherByJobnum(teacher.getJobnum()) != null) {
            return new Response<>(Response.FAIL, "工号: " + teacher.getJobnum() + "已存在", null);
        }

        if (manager.getInstituteByName(teacher.getInstitute()) == null) {
            return new Response<>(Response.FAIL, "学院: " + teacher.getInstitute() + "不存在", null);
        }

        if (manager.getMajorByName(teacher.getMajor()) == null) {
            return new Response<>(Response.FAIL, "专业: " + teacher.getMajor() + "不存在", null);
        }

        if (!manager.getMajorByName(teacher.getMajor()).getInstitute().equals(teacher.getInstitute())) {
            return new Response<>(Response.FAIL, "学院: " + teacher.getInstitute() + "中不包含专业: " + teacher.getMajor(), null);
        }

        if (teacher.getTel().length() == 0) {
            teacher.setTel("NA");
        }

        if (teacher.getEmail().length() == 0) {
            teacher.setEmail("NA");
        }

        teacher.setInitialized("N");
        teacher.setStatus("Y");
        teacher.setPassword("123abc");

        manager.createTeacher(teacher);
        return new Response<>(Response.SUCCESS, "新增教师成功", teacher);
    }

    public Response<Teacher> update(Teacher teacher) {
        if (teacher.getId() == null || manager.getTeacherById(teacher.getId()) == null) {
            return new Response<>(Response.FAIL, "被修改对象不存在", null);
        }

        if (manager.getStudentByIdentity(teacher.getIdentity()) != null) {
            return new Response<>(Response.FAIL, "身份证号: " + teacher.getIdentity() + "已存在", null);
        }

        Teacher oldTeacher = manager.getTeacherById(teacher.getId());
        Teacher newTeacher = manager.getTeacherByIdentity(teacher.getIdentity());
        if (newTeacher != null && !newTeacher.getId().equals(oldTeacher.getId())) {
            return new Response<>(Response.FAIL, "身份证号: " + teacher.getIdentity() + "已存在", null);
        }

        if (!teacher.getJobnum().equals(oldTeacher.getJobnum())) {
            return new Response<>(Response.FAIL, "工号不应被修改", null);
        }

        if (!teacher.getInitialized().equals(oldTeacher.getInitialized())) {
            return new Response<>(Response.FAIL, "密码是否初始化的状态不应被修改", null);
        }

        if (manager.getInstituteByName(teacher.getInstitute()) == null) {
            return new Response<>(Response.FAIL, "学院: " + teacher.getInstitute() + "不存在", null);
        }

        if (manager.getMajorByName(teacher.getMajor()) == null) {
            return new Response<>(Response.FAIL, "专业: " + teacher.getMajor() + "不存在", null);
        }

        if (!manager.getMajorByName(teacher.getMajor()).getInstitute().equals(teacher.getInstitute())) {
            return new Response<>(Response.FAIL, "学院: " + teacher.getInstitute() + "中不包含专业: " + teacher.getMajor(), null);
        }

        if (teacher.getTel().length() == 0) {
            teacher.setTel("NA");
        }

        if (teacher.getEmail().length() == 0) {
            teacher.setEmail("NA");
        }

        manager.updateTeacher(teacher);
        return new Response<>(Response.SUCCESS, "修改教师信息成功", teacher);
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

        CsvContainer csv = null;
        try {
            csv = csvReader.read(file, StandardCharsets.UTF_8);
        } catch(IOException e) {
            return new Response<>(Response.FAIL, "csv文件读取时发生IOException异常", null);
        }

        /* csv文件格式要求(顺序不重要)
            列名应包含：name, identity, jobnum, email, tel, major, institute
         */

        StringBuilder result = new StringBuilder("失败序号: ");
        Integer count = 0;
        Teacher teacher = null;
        Response<Teacher> response = null;
        for (CsvRow row : csv.getRows()) {
            count++;
            teacher = new Teacher();
            response = new Response<>();

//            System.out.println("*" + count.toString());
//            System.out.println("[name]" + row.getField("name"));
//            System.out.println("[identity]" + row.getField("identity"));
//            System.out.println("[jobnum]" + row.getField("jobnum"));
//            System.out.println("[email]" + row.getField("email"));
//            System.out.println("[tel]" + row.getField("tel"));
//            System.out.println("[major]" + row.getField("major"));
//            System.out.println("[institute]" + row.getField("institute"));
//            System.out.println();

            teacher.setName(row.getField("name"));
            teacher.setIdentity(row.getField("identity"));
            teacher.setJobnum(row.getField("jobnum"));
            teacher.setEmail(row.getField("email"));
            teacher.setTel(row.getField("tel"));
            teacher.setMajor(row.getField("major"));
            teacher.setInstitute(row.getField("institute"));
            response = this.create(teacher);
            if (response.getCode().equals(Response.FAIL)) {
                result.append(count.toString() + ",");
            }
        }

        // 只要文件能够读取就是成功
        // 新建失败的情况通过data部分展示
        return new Response<>(Response.SUCCESS, "批量新增教师成功", result.toString());
    }

    public Response<Teacher> delete(Integer teacherId) {
        Teacher teacher = manager.getTeacherById(teacherId);
        if (teacher == null) {
            return new Response<>(Response.FAIL, "教师id不存在", null);
        }

        // 如果教师有上课，不允许删除教师
        List<Course> courses = manager.getCoursesByTeacher(teacher);
        if (courses.size() > 0) {
            return new Response<>(Response.FAIL, "工号为" + teacher.getJobnum() + "的教师仍有上课任务，删除教师失败" , null);
        }

        // 如果教师有发出过选课申请，不允许删除教师
        List<TeacherCourseApplication> teacherCourseApplications = manager.getCourseApplicationsByTeacher(teacher);
        if (teacherCourseApplications.size() > 0) {
            return new Response<>(Response.FAIL, "工号为" + teacher.getJobnum() + "的教师仍有课程申请，删除教师失败", null);
        }

        manager.deleteTeacher(teacher);
        return new Response<>(Response.SUCCESS, "删除教师成功", teacher);
    }

}
