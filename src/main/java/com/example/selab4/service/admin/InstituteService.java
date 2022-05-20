package com.example.selab4.service.admin;

import com.example.selab4.manager.admin.InstituteManager;
import com.example.selab4.model.entity.Institute;
import com.example.selab4.model.entity.Major;
import com.example.selab4.util.Response;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    public Response<Institute> delete(Integer instituteId) {
        Institute institute = manager.getInstituteById(instituteId);
        if (institute == null) {
            return new Response<>(Response.FAIL, "学院id: " + instituteId + "不存在", null);
        }

        // 如果学院还有专业，不允许删除学院
        List<Major> majors = manager.getMajors(institute);
        if (majors.size() > 0) {
            return new Response<>(Response.FAIL, "学院: " + institute.getName() + "还有专业存在，删除学院失败", null);
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

        manager.updateInstitute(institute);

        return new Response<>(Response.SUCCESS, "修改学院成功", institute);
    }

    public Response<List<Institute>> getAll() {
        return new Response<>(Response.SUCCESS, "查找所有学院成功", manager.getAllInstitutes());
    }

    public String getNameById(Integer id){
        return manager.getInstituteById(id).getName();
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
            列名应包含：name
         */

        StringBuilder result = new StringBuilder("失败序号: ");
        Integer count = 0;
        Institute institute = null;
        Response<Institute> response = null;
        for (CsvRow row : csv.getRows()) {
            count++;
            institute = new Institute();
            response = new Response<>();

            institute.setName(row.getField("name"));
            response = this.create(institute);
            if (response.getCode().equals(Response.FAIL)) {
                result.append(count.toString() + ",") ;
            }
        }

        // 只要文件能够读取就是成功
        // 新建失败的情况通过data部分展示
        return new Response<>(Response.SUCCESS, "批量新增学院成功", result.toString());
    }
}
