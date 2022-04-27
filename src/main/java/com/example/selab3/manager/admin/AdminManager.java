package com.example.selab3.manager.admin;

import com.example.selab3.mapper.AdminMapper;
import com.example.selab3.model.entity.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("AdminAdminManager")
public class AdminManager {
    private final AdminMapper adminMapper;
    @Autowired
    AdminManager(AdminMapper adminMapper){
        this.adminMapper=adminMapper;
    }

    public void saveAdmin(Administrator administrator){
        adminMapper.save(administrator);
    }

    public Administrator findAdminById(Integer id){
        return adminMapper.findAdministratorById(id);
    }
}
