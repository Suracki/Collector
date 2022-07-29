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

    /**
     * Mapping for GET
     *
     * Serves page to list collection for Admin user to perform management
     *
     * @param model Model
     * @return manage collection page with full collection stored in Model
     */
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

    /**
     * Mapping for GET
     *
     * Serves page to list collection for Admin user to perform management
     * This version of the page is filtered by collection Item type
     *
     * @param model Model
     * @param type String
     * @return manage collection page with all items of matching Type stored in Model
     */
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

    /**
     * Mapping for GET
     *
     * Serves page to list collection for Admin user to perform management
     * This version of the page is used for changing page when viewing a collection of over 10 items
     * The current/next page is taken from session details, as is the Item type to view
     *
     * @param model Model
     * @return manage collection page with subset of items of matching Type stored in Model
     */
    @GetMapping("/admin/managePaged")
    public String viewPaged(Model model) {
        logger.info("User connected to /guest/viewPaged endpoint with type");
        return adminService.managePaged(model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to list collection for Admin user to perform management
     * This version of the page is used for changing page when viewing a collection of over 10 items
     * The current/prev page is taken from session details, as is the Item type to view
     *
     * @param model Model
     * @return manage collection page with subset of items of matching Type stored in Model
     */
    @GetMapping("/admin/managePagedBack")
    public String viewPagedBack(Model model) {
        logger.info("User connected to /guest/viewPaged endpoint with type");
        return adminService.managePagedBack(model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to add an item to the collection
     *
     * @param model Model
     * @param item Item
     * @return add item page
     */
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

    /**
     * Mapping for POST
     *
     * Requests validation of Item created via addItem page
     *
     * @param model Model
     * @param item Item
     * @param result BindingResult
     * @return collection manage page if successful, add item page if errors were found in new Item
     */
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

    /**
     * Mapping for GET
     *
     * Attempts to delete an Item from the collection then reloads management page
     *
     * @param model Model
     * @param id Integer
     * @return collection management page
     */
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

    /**
     * Mapping for GET
     *
     * Serves page to update an item in the collection
     *
     * @param model Model
     * @param id Integer
     * @return update item page
     */
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

    /**
     * Mapping for POST
     *
     * Requests validation of Item submitted via update page
     *
     * @param model Model
     * @param id Integer
     * @param item Item
     * @param result BindingResult
     * @return manage item page with updates if successful, back to update item page if errors found
     */
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

    /**
     * Mapping for GET
     *
     * Serves page to view details for an MTG card
     * Details are obtained from Scryfall API if not already loaded into system
     *
     * @param model Model
     * @param set_code String, MTG 3 character set code
     * @param collectors_number String, MTG card collector's number
     * @return card details page
     */
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
