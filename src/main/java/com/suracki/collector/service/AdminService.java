package com.suracki.collector.service;

import com.suracki.collector.datamonitor.Monitor;
import com.suracki.collector.domain.Item;
import com.suracki.collector.domain.MtgCard;
import com.suracki.collector.domain.User;
import com.suracki.collector.external.Scryfall;
import com.suracki.collector.external.dto.ScryfallCard;
import com.suracki.collector.repository.ItemRepository;
import com.suracki.collector.repository.LocationRepository;
import com.suracki.collector.repository.MtgCardRepository;
import com.suracki.collector.repository.UserRepository;
import com.suracki.collector.security.SessionDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class AdminService extends BaseService{

    private UserRepository userRepository;

    private static Monitor monitor;


    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public AdminService(UserRepository userRepository,
                        ItemRepository itemRepository,
                        LocationRepository locationRepository,
                        MtgCardRepository mtgCardRepository) {
        super(itemRepository, locationRepository, mtgCardRepository);
        this.userRepository = userRepository;
        scryfall = new Scryfall();
        monitor = new Monitor(mtgCardRepository, sessions);
        logger.info("AdminService created");
        addShutDownHook();
        setupAdmin();
    }

    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                monitor.stopMonitor();
            }
        });
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

    public String manage(Model model) {
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
        model.addAttribute("items", itemRepository.findAll());
        return manageByType("all", model);
    }

    public String manageByType(String type, Model model) {
        super.addTypes(model);
        SessionDetails sessionDetails = getSession();
        sessionDetails.setLastViewedType(type);
        sessionDetails.setLastPageNumber(0);
        return managePaged(model);
    }

    public String managePaged(Model model) {
        super.addTypes(model);
        SessionDetails sessionDetails = getSession();
        String type = sessionDetails.getLastViewedType();
        Integer page = sessionDetails.getLastPageNumber() + 1;

        List<Item> items;
        if (type.equals("all")) {
            items = itemRepository.findAll();
        }
        else {
            items = itemRepository.findByType(type);
        }
        List<Item> displayItems;

        int stId = page*10 - 10;
        int endId = page*10;

        if (page == 1) {
            model.addAttribute("firstPage", "true");
        }
        else {
            model.addAttribute("firstPage", "false");
        }

        if (page*10 > items.size()-1) {
            model.addAttribute("lastPage", "true");
        }
        else {
            model.addAttribute("lastPage", "false");
        }

        if (items.size() < stId) {
            logger.error("viewPaged attempting to display page too far ahead");
            model.addAttribute("page", page);
            model.addAttribute("collection", new ArrayList<Item>());
            return "admin/managePaged";
        }
        else if (items.size() < endId) {
            displayItems = items.subList(stId,items.size());
            logger.debug("viewPaged loaded " + items.size() + " items.");
            model.addAttribute("page", page);
            model.addAttribute("collection", displayItems);
        }
        else {
            displayItems = items.subList(stId,endId);
            logger.debug("viewPaged loaded " + items.size() + " items.");
            model.addAttribute("page", page);
            model.addAttribute("collection", displayItems);
        }
        sessionDetails.setLastPageNumber(page);
        return "admin/managePaged";

    }

    public String managePagedBack(Model model) {
        super.addTypes(model);
        SessionDetails sessionDetails = getSession();
        String type = sessionDetails.getLastViewedType();
        Integer page = sessionDetails.getLastPageNumber() - 1;

        List<Item> items;
        if (type.equals("all")) {
            items = itemRepository.findAll();
        }
        else {
            items = itemRepository.findByType(type);
        }
        List<Item> displayItems;

        int stId = page*10 - 10;
        int endId = page*10;

        if (page == 1) {
            model.addAttribute("firstPage", "true");
            model.addAttribute("lastPage", "false");
        }
        else {
            model.addAttribute("firstPage", "false");
            model.addAttribute("lastPage", "false");
        }

        if (items.size() < stId) {
            logger.error("viewPaged attempting to display page too far ahead");
            model.addAttribute("page", page);
            model.addAttribute("collection", new ArrayList<Item>());
            return "admin/managePaged";
        }
        else if (items.size() < endId) {
            displayItems = items.subList(stId,items.size());
            logger.debug("viewPaged loaded " + items.size() + " items.");
            model.addAttribute("page", page);
            model.addAttribute("collection", displayItems);
        }
        else {
            displayItems = items.subList(stId,endId);
            logger.debug("viewPaged loaded " + items.size() + " items.");
            model.addAttribute("page", page);
            model.addAttribute("collection", displayItems);
        }
        sessionDetails.setLastPageNumber(page);
        return "admin/managePaged";

    }

    public String addItem(Model model, Item item) {
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
        return "admin/addItem";
    }

    public String validate(@Valid Item item, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            itemRepository.save(item);
            if (item.getType().equals("MTG")) {
                if (mtgCardRepository.findBySetAndCollectorNumber(item.getDetail(), String.valueOf(item.getItemNumber())) == null) {
                    logger.info("Card details not found in MtgCardRepository: " + item.getItemName());
                    ScryfallCard scryfallCard = scryfall.getCardInfo(item.getDetail(), item.getItemNumber());
                    MtgCard mtgCard = new MtgCard(scryfallCard);
                    mtgCardRepository.save(mtgCard);
                }
                else {
                    logger.info("Card details already stored in MtgCardRepository: " + item.getItemName());
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

        //Request price update if
        if (Duration.between(mtgCard.getPriceUpdateTime(), LocalDateTime.now()).toDays() > 7) {
            logger.info("Requesting update for " + mtgCard.getName() + " - " + mtgCard.getSet_name());
            monitor.updatePrice(mtgCard);
        }
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
        model.addAttribute("mtgCard", mtgCard);
        return "admin/cardDetails";
    }
}
