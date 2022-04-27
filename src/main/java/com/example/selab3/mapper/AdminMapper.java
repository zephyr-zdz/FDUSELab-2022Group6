package com.example.selab3.mapper;

import com.example.selab3.model.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper extends JpaRepository<Administrator, Integer> {
    Administrator findAdministratorByAdminnumAndPassword(String adminnum, String password);
    Administrator findAdministratorById(Integer id);
}
