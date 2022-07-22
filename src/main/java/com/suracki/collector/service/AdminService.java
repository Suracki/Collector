package com.suracki.collector.service;

import com.suracki.collector.datamonitor.Monitor;
import com.suracki.collector.domain.Item;
import com.suracki.collector.domain.Location;
import com.suracki.collector.domain.MtgCard;
import com.suracki.collector.domain.User;
import com.suracki.collector.external.Scryfall;
import com.suracki.collector.external.dto.ScryfallCard;
import com.suracki.collector.repository.ItemRepository;
import com.suracki.collector.repository.LocationRepository;
import com.suracki.collector.repository.MtgCardRepository;
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
import java.util.Locale;

@Service
public class AdminService extends BaseService{

    private UserRepository userRepository;

    private final Monitor monitor;
    private Scryfall scryfall;


    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public AdminService(UserRepository userRepository,
                        ItemRepository itemRepository,
                        LocationRepository locationRepository,
                        MtgCardRepository mtgCardRepository) {
        super(itemRepository, locationRepository, mtgCardRepository);
        this.userRepository = userRepository;
        monitor = new Monitor(mtgCardRepository);
        scryfall = new Scryfall();
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
            if (item.getType().equals("MTG")) {
                if (mtgCardRepository.findBySetAndCollectorNumber(String.valueOf(item.getItemNumber()), item.getDetail()) == null) {
                    ScryfallCard scryfallCard = scryfall.getCardInfo(item.getDetail(), item.getItemNumber());
                    MtgCard mtgCard = new MtgCard(scryfallCard);
                    mtgCardRepository.save(mtgCard);
                }
            }
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

    public String cardDetails(Model model, String set_code, String collectors_number) {
        MtgCard mtgCard = mtgCardRepository.findBySetAndCollectorNumber(set_code, collectors_number);
        if (mtgCard == null) {
            ScryfallCard scryfallCard = scryfall.getCardInfo(set_code, Integer.parseInt(collectors_number));
            mtgCard = new MtgCard(scryfallCard);
            mtgCardRepository.save(mtgCard);
        }
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
        model.addAttribute("mtgCard", mtgCard);
        return "admin/cardDetails";
    }
}
