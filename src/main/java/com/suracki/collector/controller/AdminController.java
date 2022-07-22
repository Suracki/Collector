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
        return adminService.home(model);
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
        return adminService.homeFiltered(type, model);
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

//    @GetMapping("/admin/testScry/")
//    public String testScryfall(Model model) {
//        Scryfall scryfall = new Scryfall();
//        ScryfallCard card = scryfall.getCardInfo("ori", 60);
//        System.out.println("Name: " + card.getName());
//        System.out.println("Set: " + card.getSet_name());
//        System.out.println("Value: " + card.getPrices().get("eur") + " EUR");
//        return "home";
//    }

}
