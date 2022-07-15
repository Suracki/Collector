package com.suracki.collector.service;

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
        model.addAttribute("items", itemRepository.findAll());
        return "admin/manage";
    }
}
