package com.suracki.collector.controller;

import com.suracki.collector.domain.Item;
import com.suracki.collector.security.RoleCheck;
import com.suracki.collector.service.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class AdminController {

    private static final Logger logger = LogManager.getLogger(HomeController.class);

    @Autowired
    AdminService adminService;

    @Autowired
    private RoleCheck roleCheck;

    @RequestMapping("/admin/manage")
    public String adminHome(Model model)
    {
        logger.info("User connected to admin/manage endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "/";
        }
        return adminService.home(model);
    }

    @RequestMapping("/admin/addItem")
    public String addItem(Item item)
    {
        logger.info("User connected to admin/manage endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "/";
        }
        return adminService.addItem(item);
    }

    @PostMapping("/admin/addItem/validate")
    public String validate(@Valid Item item, BindingResult result, Model model) {
        logger.info("User connected to admin/manage endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "/";
        }
        return adminService.validate(item, result, model);
    }

}
