package com.pwj.admin.controller;

import com.pwj.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/admin")
    public String adminIndex() {
        return "admin";
    }

    @PostMapping("/login")
    public String adminLogin(@RequestParam("form-username") String username, @RequestParam("form-password") String password) {
        System.out.println(username + password);
        boolean flag = adminService.isUser(username, password);
        System.out.println(flag);

        return "shouye";
    }

}
