package com.suracki.collector.controller;

import com.suracki.collector.service.CollectionService;
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
public class GuestController {

    private static final Logger logger = LogManager.getLogger(GuestController.class);

    @Autowired
    CollectionService collectionService;

    /**
     * Mapping for GET
     *
     * Serves welcome page for guests, prompts to select an Item type
     *
     * @param model Model
     * @return guest home page
     */
    @RequestMapping("/guest/viewHome")
    public String home(Model model)
    {
        logger.info("User connected to /guest/viewHome endpoint");
        return collectionService.guestViewHome(model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to list collection for guest user
     * The page is filtered by collection Item type
     *
     * @param model Model
     * @param type String
     * @return manage collection page with all items of matching Type stored in Model
     */
    @GetMapping("/guest/viewByType")
    public String viewByType(@RequestParam(value="type") String type, Model model) {
        logger.info("User connected to /guest/viewByType/{type} endpoint with type " + type);
        return collectionService.viewByType(type, model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to list collection for guest user
     * This version of the page is used for changing page when viewing a collection of over 10 items
     * The current/next page is taken from session details, as is the Item type to view
     *
     * @param model Model
     * @return manage collection page with subset of items of matching Type stored in Model
     */
    @GetMapping("/guest/viewPaged")
    public String viewPaged(Model model) {
        logger.info("User connected to /guest/viewPaged endpoint with type");
        return collectionService.viewPaged(model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to list collection for guest user
     * This version of the page is used for changing page when viewing a collection of over 10 items
     * The current/prev page is taken from session details, as is the Item type to view
     *
     * @param model Model
     * @return manage collection page with subset of items of matching Type stored in Model
     */
    @GetMapping("/guest/viewPagedBack")
    public String viewPagedBack(Model model) {
        logger.info("User connected to /guest/viewPaged endpoint with type");
        return collectionService.viewPagedBack(model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to list collection for guest user
     * The page is filtered by Item name
     *
     * @param model Model
     * @param name String
     * @return manage collection page with all items of matching Name stored in Model
     */
    @GetMapping("/guest/filterName")
    public String viewFilterName(@RequestParam(value="name") String name, Model model) {
        logger.info("User connected to /guest/filterName/{name} endpoint with name " + name);
        return collectionService.viewByName(name, model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to list collection for guest user
     * The page is filtered by Item detail (eg MTG set, LEGO theme)
     *
     * @param model Model
     * @param detail String
     * @return manage collection page with all items of matching detail stored in Model
     */
    @GetMapping("/guest/filterDetail")
    public String viewFilterDetail(@RequestParam(value="detail") String detail, Model model) {
        logger.info("User connected to /guest/filterDetail/{detail} endpoint with detail " + detail);
        return collectionService.viewByDetail(detail, model);
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
    @GetMapping("/guest/cardDetails")
    public String cardDetails(@RequestParam(value="set_code") String set_code, @RequestParam(value="collectors_number") String collectors_number,
                              Model model){
        logger.info("User connected to /guest/cardDetails endpoint for card with set " + set_code + " and number " + collectors_number);
        return collectionService.cardDetails(model, set_code, collectors_number);
    }



}
