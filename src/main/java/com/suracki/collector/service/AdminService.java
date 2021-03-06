package com.suracki.collector.service;

import com.suracki.collector.domain.Item;
import com.suracki.collector.domain.Location;
import com.suracki.collector.domain.User;
import com.suracki.collector.repository.ItemRepository;
import com.suracki.collector.repository.LocationRepository;
import com.suracki.collector.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class AdminService {

    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private LocationRepository locationRepository;


    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public AdminService(UserRepository userRepository,
                        ItemRepository itemRepository,
                        LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        logger.info("AdminService created");
        setupAdmin();
    }

    private void setupAdmin() {
        User admin = userRepository.findByUsername("Admin");
        if (admin == null) {
            logger.info("Admin User Not Found");
            admin = new User();
            admin.setFullname("Simon");
            admin.setRole("Admin");
            admin.setUsername("Admin");
            admin.setPassword("$2a$12$3I2P2stDCpA0Hv/PUqQcEe3ivmjGu4EOHZmRNOgNz0Hg2xnjL7CtG");
            userRepository.save(admin);
            logger.info("Default Admin User Created");
        }
        else {
            logger.info("Admin User Found");
        }
    }

    public String home(Model model) {
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
        model.addAttribute("items", itemRepository.findAll());
        return "admin/manage";
    }

    public String homeFiltered(String type, Model model) {
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
        model.addAttribute("items", itemRepository.findByType(type));
        return "admin/manage";
    }

    public String addItem(Model model, Item item) {
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
        return "admin/addItem";
    }

    public String validate(@Valid Item item, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            itemRepository.save(item);
            model.addAttribute("items", itemRepository.findAll());
            return "redirect:/admin/manage";
        }
        return "admin/addItem";
    }

    public String showUpdateForm(Integer id, Model model) {
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
        Item item = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
        model.addAttribute("item", item);
        return "admin/editItem";
    }

    public String updateItem(Integer id, Item item, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/editItem";
        }
        item.setItemId(id);
        itemRepository.save(item);
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
        model.addAttribute("items", itemRepository.findByType(item.getType()));
        return "admin/manage";
    }

    public String deleteItem(Integer id, Model model) {
        itemRepository.deleteById(id);
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
        model.addAttribute("items", itemRepository.findAll());
        return "admin/manage";
    }
}
