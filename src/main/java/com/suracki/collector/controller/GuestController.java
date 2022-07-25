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

    @RequestMapping("/guest/viewHome")
    public String home(Model model)
    {
        logger.info("User connected to /guest/viewHome endpoint");
        return collectionService.guestViewHome(model);
    }

    @GetMapping("/guest/viewByType")
    public String viewByType(@RequestParam(value="type") String type, Model model) {
        logger.info("User connected to /guest/viewByType/{type} endpoint with type " + type);
        return collectionService.viewByType(type, model);
    }

    @GetMapping("/guest/viewPaged")
    public String viewPaged(Model model) {
        logger.info("User connected to /guest/viewPaged endpoint with type");
        return collectionService.viewPaged(model);
    }

    @GetMapping("/guest/viewPagedBack")
    public String viewPagedBack(Model model) {
        logger.info("User connected to /guest/viewPaged endpoint with type");
        return collectionService.viewPagedBack(model);
    }

    @GetMapping("/guest/filterName")
    public String viewFilterName(@RequestParam(value="name") String name, Model model) {
        logger.info("User connected to /guest/filterName/{name} endpoint with name " + name);
        return collectionService.viewByName(name, model);
    }

    @GetMapping("/guest/filterDetail")
    public String viewFilterDetail(@RequestParam(value="detail") String detail, Model model) {
        logger.info("User connected to /guest/filterDetail/{detail} endpoint with detail " + detail);
        return collectionService.viewByDetail(detail, model);
    }

    @GetMapping("/guest/cardDetails")
    public String cardDetails(@RequestParam(value="set_code") String set_code, @RequestParam(value="collectors_number") String collectors_number,
                              Model model){
        logger.info("User connected to /guest/cardDetails endpoint for card with set " + set_code + " and number " + collectors_number);
        return collectionService.cardDetails(model, set_code, collectors_number);
    }



}
