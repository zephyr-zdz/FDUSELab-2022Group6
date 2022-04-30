package com.example.selab4.service.admin;

import com.example.selab4.manager.admin.MajorManager;
import com.example.selab4.model.entity.Institute;
import com.example.selab4.model.entity.Major;
import com.example.selab4.model.entity.Student;
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

@Service("AdminMajorService")
public class MajorService {
    private final MajorManager manager;

    @Autowired
    public MajorService(MajorManager manager) {
        this.manager = manager;
    }

    public Response<Major> get(String majorName) {
        Major major = manager.getMajorByName(majorName);
        if (major == null) {
            return new Response<>(Response.FAIL, "专业: " + majorName + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "查找专业成功", major);
    }

    public Response<Major> create(Major major) {
        if (major.getId() != null) {
            return new Response<>(Response.FAIL, "专业id由系统自动生成", null);
        }

        if (manager.getMajorByName(major.getName()) != null) {
            return new Response<>(Response.FAIL, "专业: " + major.getName() + "已存在", null);
        }

        Institute institute = manager.getInstituteByName(major.getInstitute());
        if (institute == null) {
            return new Response<>(Response.FAIL, "所在学院: " + major.getInstitute() + "不存在!", null);
        }

        manager.createMajor(major);
        return new Response<>(Response.SUCCESS, "新增专业成功", major);
    }

    public Response<Major> delete(Integer majorId) {
        Major major = manager.getMajorById(majorId);
        if (major == null) {
            return new Response<>(Response.FAIL, "专业id: " + majorId + "不存在", null);
        }

        // 如果专业还有学生，不允许删除专业
        List<Student> students = manager.getStudentsByMajor(major);
        if (students.size() > 0) {
            return new Response<>(Response.FAIL, "专业: " + major.getName() + "还有学生，删除专业失败", null);
        }

        // 如果专业还有教师，不允许删除专业
        List<Teacher> teachers = manager.getTeachersByMajor(major);
        if (teachers.size() > 0) {
            return new Response<>(Response.FAIL, "专业: " + major.getName() + "还有教师，删除专业失败", null);
        }

        manager.deleteMajor(major);
        return new Response<>(Response.SUCCESS, "删除专业成功", major);
    }

    public Response<Major> update(Major major) {
        if (major.getId() == null || manager.getMajorById(major.getId()) == null) {
            return new Response<>(Response.FAIL, "被修改对象不存在", null);
        }

        Major oldMajor = manager.getMajorById(major.getId());
        Major newMajor = manager.getMajorByName(major.getName());
        if (newMajor != null && !newMajor.getId().equals(oldMajor.getId())) {
            return new Response<>(Response.FAIL, "专业: " + major.getName() + "已存在", null);
        }

        Institute institute = manager.getInstituteByName(major.getInstitute());
        if (institute == null) {
            return new Response<>(Response.FAIL, "所在学院: " + major.getInstitute() + "不存在", null);
        }

        // 级联的update
        manager.updateStudentsByMajor(oldMajor, major);
        manager.updateTeachersByMajor(oldMajor, major);

        oldMajor.update(major);
        manager.updateMajor(oldMajor);
        return new Response<>(Response.SUCCESS, "修改专业成功", oldMajor);
    }

    public Response<List<Major>> getAll() {
        return new Response<>(Response.SUCCESS, "查找所有专业成功", manager.getAllMajors());
    }

    public Response<List<Major>> getAllByInstitute(String name) {
        Institute institute = manager.getInstituteByName(name);
        if (institute == null) {
            return new Response<>(Response.FAIL, "学院: " + name + "不存在", null);
        }

        return new Response<>(Response.SUCCESS, "查找学院: " + name + "的所有专业成功", manager.getAllMajorsByInstitute(institute.getName()));
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
            列名应包含：name, institute
         */

        StringBuilder result = new StringBuilder("失败序号: ");
        Integer count = 0;
        Major major = null;
        Response<Major> response = null;
        for (CsvRow row : csv.getRows()) {
            count++;
            major = new Major();
            response = new Response<>();

            major.setName(row.getField("name"));
            major.setInstitute(row.getField("institute"));
            response = this.create(major);
            if (response.getCode().equals(Response.FAIL)) {
                result.append(count.toString() + ",") ;
            }
        }

        // 只要文件能够读取就是成功
        // 新建失败的情况通过data部分展示
        return new Response<>(Response.SUCCESS, "批量新增专业成功", result.toString());
    }
}
