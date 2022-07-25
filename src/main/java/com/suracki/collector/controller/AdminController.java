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
import org.springframework.web.bind.annotation.*;

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
            return "home";
        }
        return adminService.manage(model);
    }

    @RequestMapping("/admin/manageByType")
    public String adminHomeFiltered(@RequestParam(value="type") String type, Model model)
    {
        logger.info("User connected to admin/manage endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "home";
        }
        return adminService.manageByType(type, model);
    }

    @GetMapping("/admin/managePaged")
    public String viewPaged(Model model) {
        logger.info("User connected to /guest/viewPaged endpoint with type");
        return adminService.managePaged(model);
    }

    @GetMapping("/admin/managePagedBack")
    public String viewPagedBack(Model model) {
        logger.info("User connected to /guest/viewPaged endpoint with type");
        return adminService.managePagedBack(model);
    }

    @RequestMapping("/admin/addItem")
    public String addItem(Model model, Item item)
    {
        logger.info("User connected to admin/manage endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "home";
        }
        return adminService.addItem(model, item);
    }

    @PostMapping("/admin/addItem/validate")
    public String validate(@Valid Item item, BindingResult result, Model model) {
        logger.info("User connected to admin/manage endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "home";
        }
        return adminService.validate(item, result, model);
    }


    @GetMapping("/admin/deleteItem/{id}")
    public String deleteItem(@PathVariable("id") Integer id, Model model) {
        logger.info("User connected to admin/deleteItem endpoint for item with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "home";
        }
        return adminService.deleteItem(id, model);
    }

    @GetMapping("/admin/updateItem/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("User connected to admin/updateItem GET endpoint for item with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "home";
        }
        return adminService.showUpdateForm(id, model);
    }

    @PostMapping("/admin/updateItem/{id}")
    public String updateItem(@PathVariable("id") Integer id, Model model,
                             @Valid Item item, BindingResult result) {
        logger.info("User connected to admin/updateItem POST endpoint for item with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "home";
        }
        return adminService.updateItem(id, item, result, model);
    }

    @GetMapping("/admin/cardDetails")
    public String cardDetails(@RequestParam(value="set_code") String set_code, @RequestParam(value="collectors_number") String collectors_number,
                              Model model){
        logger.info("User connected to /admin/cardDetails endpoint for card with set " + set_code + " and number " + collectors_number);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "home";
        }
        return adminService.cardDetails(model, set_code, collectors_number);
    }


}
