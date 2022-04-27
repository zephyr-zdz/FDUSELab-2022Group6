package com.example.selab3.service.admin;

import com.example.selab3.manager.admin.StudentManager;
import com.example.selab3.model.entity.Course;
import com.example.selab3.model.entity.Student;
import com.example.selab3.util.Response;
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

@Service("AdminStudentService")
public class StudentService {
    private final StudentManager manager;

    @Autowired
    public StudentService(StudentManager manager) {
        this.manager = manager;
    }

    public Response<List<Student>> getAll() {
        return new Response<>(Response.SUCCESS, "查找所有学生成功", manager.getAllStudents());
    }

    public Response<Student> create(Student student) {
        if (student.getId() != null) {
            return new Response<>(Response.FAIL, "学生id由系统自动生成", null);
        }

        if (manager.getStudentByIdentity(student.getIdentity()) != null || manager.getTeacherByIdentity(student.getIdentity()) != null) {
            return new Response<>(Response.FAIL, "身份证号: " + student.getIdentity() + "已存在", null);
        }

        if (manager.getStudentByStunum(student.getStunum()) != null) {
            return new Response<>(Response.FAIL, "学号: " + student.getStunum() + "已存在", null);
        }

        if (manager.getInstituteByName(student.getInstitute()) == null) {
            return new Response<>(Response.FAIL, "学院: " + student.getInstitute() + "不存在", null);
        }

        if (manager.getMajorByName(student.getMajor()) == null) {
            return new Response<>(Response.FAIL, "专业: " + student.getMajor() + "不存在", null);
        }

        if (!manager.getMajorByName(student.getMajor()).getInstitute().equals(student.getInstitute())) {
            return new Response<>(Response.FAIL, "学院: " + student.getInstitute() + "中不包含专业: " + student.getMajor(), null);
        }

        if (student.getTel().length() == 0) {
            student.setTel("NA");
        }

        if (student.getEmail().length() == 0) {
            student.setEmail("NA");
        }

        student.setInitialized("N");
        student.setStatus("Y");
        student.setPassword("123abc");

        manager.createStudent(student);
        return new Response<>(Response.SUCCESS, "新增学生成功", student);
    }

    public Response<Student> update(Student student) {
        if (student.getId() == null || manager.getStudentById(student.getId()) == null) {
            return new Response<>(Response.FAIL, "被修改对象不存在", null);
        }

        if (manager.getTeacherByIdentity(student.getIdentity()) != null) {
            return new Response<>(Response.FAIL, "身份证号: " + student.getIdentity() + "已存在", null);
        }

        Student oldStudent = manager.getStudentById(student.getId());
        Student newStudent = manager.getStudentByIdentity(student.getIdentity());
        if (newStudent != null && !newStudent.getId().equals(oldStudent.getId())) {
            return new Response<>(Response.FAIL, "身份证号: " + student.getIdentity() + "已存在", null);
        }

        if (!student.getStunum().equals(oldStudent.getStunum())) {
            return new Response<>(Response.FAIL, "学号不应被修改", null);
        }

        if (!student.getInitialized().equals(oldStudent.getInitialized())) {
            return new Response<>(Response.FAIL, "密码是否初始化的状态不应被修改", null);
        }

        if (manager.getInstituteByName(student.getInstitute()) == null) {
            return new Response<>(Response.FAIL, "学院: " + student.getInstitute() + "不存在", null);
        }

        if (manager.getMajorByName(student.getMajor()) == null) {
            return new Response<>(Response.FAIL, "专业: " + student.getMajor() + "不存在", null);
        }

        if (!manager.getMajorByName(student.getMajor()).getInstitute().equals(student.getInstitute())) {
            return new Response<>(Response.FAIL, "学院: " + student.getInstitute() + "中不包含专业: " + student.getMajor(), null);
        }

        if (student.getTel().length() == 0) {
            student.setTel("NA");
        }

        if (student.getEmail().length() == 0) {
            student.setEmail("NA");
        }

        oldStudent.update(student);
        manager.updateStudent(oldStudent);
        return new Response<>(Response.SUCCESS, "修改学生信息成功", oldStudent);
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
            列名应包含：name, identity, stunum, email, tel, major, institute
         */

        String result = "失败序号: "; // 以后有空再用stringbuilder优化
        Integer count = 0;
        Student student = null;
        Response<Student> response = null;
        for (CsvRow row : csv.getRows()) {
            count++;
            student = new Student();
            response = new Response<>();

//            System.out.println("*" + count.toString());
//            System.out.println("[name]" + row.getField("name"));
//            System.out.println("[identity]" + row.getField("identity"));
//            System.out.println("[stunum]" + row.getField("stunum"));
//            System.out.println("[email]" + row.getField("email"));
//            System.out.println("[tel]" + row.getField("tel"));
//            System.out.println("[major]" + row.getField("major"));
//            System.out.println("[institute]" + row.getField("institute"));
//            System.out.println();

            student.setName(row.getField("name"));
            student.setIdentity(row.getField("identity"));
            student.setStunum(row.getField("stunum"));
            student.setEmail(row.getField("email"));
            student.setTel(row.getField("tel"));
            student.setMajor(row.getField("major"));
            student.setInstitute(row.getField("institute"));
            response = this.create(student);
            if (response.getCode().equals(Response.FAIL)) {
                result += count.toString() + ",";
            }
        }

        // 只要文件能够读取就是成功
        // 新建失败的情况通过data部分展示
        return new Response<>(Response.SUCCESS, "批量创建学生成功", result);
    }

    public Response<Student> delete(String stunum) {
        Student student = manager.getStudentByStunum(stunum);
        if (student == null) {
            return new Response<>(Response.FAIL, "学号不存在", null);
        }

        // 如果学生有选课，不允许删除学生
        List<Course> courses = manager.getCoursesByStudent(student);
        if (courses.size() > 0) {
            return new Response<>(Response.FAIL, "学号为" + stunum + "的学生仍有选上课程，删除学生失败", null);
        }

        manager.deleteStudent(student);
        return new Response<>(Response.SUCCESS, "删除学生成功", student);
    }
}
