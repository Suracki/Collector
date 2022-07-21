package com.suracki.collector.service;

import com.suracki.collector.domain.Item;
import com.suracki.collector.domain.Location;
import com.suracki.collector.repository.ItemRepository;
import com.suracki.collector.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class CollectionService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(CollectionService.class);

    public CollectionService(ItemRepository itemRepository, LocationRepository locationRepository) {
        super(itemRepository, locationRepository);
        logger.info("CollectionService created");
        dummySetup();
    }

    private void dummySetup() {
        List<Location> load = locationRepository.findAll();
        if (load.size() != 0) {
            logger.info("Database already contains items. Skipping default setup.");
            return;
        }
        logger.info("Database is empty. Adding default items.");

        setupDefaultStorage();
        setupDefaultItems();
    }
    private void setupDefaultStorage() {
        Location marvelBox = new Location();
        Location originBinder = new Location();
        marvelBox.setStorageName("Marvel Longbox");
        originBinder.setStorageName("Origins Binder");
        locationRepository.save(marvelBox);
        locationRepository.save(originBinder);
        logger.info("Default storage added.");
    }
    private void setupDefaultItems() {

        Item xmen = new Item();
        Item avengers = new Item();
        Item jace = new Item();
        Item land = new Item();

        xmen.setItemName("Uncanny X-Men");
        xmen.setItemNumber(600);
        xmen.setType("Comic");
        xmen.setStorageLocation("Marvel Longbox");

        avengers.setItemName("Uncanny Avengers");
        avengers.setItemNumber(1);
        avengers.setType("Comic");
        avengers.setStorageLocation("Marvel Longbox");

        jace.setItemName("Jace, Vryn's Prodigy / Jace, Telepath Unbound");
        jace.setItemNumber(60);
        jace.setItemCondition("NM");
        jace.setItemQuantity(1);
        jace.setDetail("ORI");
        jace.setType("MTG");
        jace.setStorageLocation("Origins Binder");

        land.setItemName("Fire-Lit Thicket");
        land.setItemNumber(29);
        land.setItemCondition("NM");
        land.setItemQuantity(1);
        land.setDetail("EXP");
        land.setType("MTG");
        land.setStorageLocation("Origins Binder");

        itemRepository.save(xmen);
        itemRepository.save(avengers);
        itemRepository.save(jace);
        itemRepository.save(land);

        logger.info("Default items added.");

    }

    public String guestViewHome(Model model) {
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
        return "guest/viewHome";
    }

    public String viewByType(String type, Model model) {
        super.addTypes(model);
        model.addAttribute("collection", itemRepository.findByType(type));
        return "guest/view";
    }

    public String viewByName(String name, Model model) {
        super.addTypes(model);
        model.addAttribute("collection", itemRepository.findByName(name));
        return "guest/view";
    }

    public String viewByDetail(String detail, Model model) {
        super.addTypes(model);
        model.addAttribute("collection", itemRepository.findByDetail(detail));
        return "guest/view";
    }
}
