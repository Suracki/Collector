package com.suracki.collector.service;

import com.suracki.collector.domain.Item;
import com.suracki.collector.domain.Location;
import com.suracki.collector.domain.User;
import com.suracki.collector.repository.ItemRepository;
import com.suracki.collector.repository.LocationRepository;
import com.suracki.collector.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {

    ItemRepository itemRepository;
    LocationRepository locationRepository;

    private static final Logger logger = LoggerFactory.getLogger(CollectionService.class);

    public CollectionService(ItemRepository itemRepository, LocationRepository locationRepository) {
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
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

        int marvelBoxId = locationRepository.findByName("Marvel Longbox").getLocationId();
        int originsBinderId = locationRepository.findByName("Origins Binder").getLocationId();

        Item xmen = new Item();
        Item avengers = new Item();
        Item jace = new Item();
        Item land = new Item();

        xmen.setItemName("Uncanny X-Men");
        xmen.setItemNumber(600);
        xmen.setType("Comic");
        xmen.setStorageLocation(marvelBoxId);

        avengers.setItemName("Uncanny Avengers");
        avengers.setItemNumber(1);
        avengers.setType("Comic");
        avengers.setStorageLocation(marvelBoxId);

        jace.setItemName("Jace, Vryn's Prodigy / Jace, Telepath Unbound");
        jace.setItemNumber(60);
        jace.setItemCondition("NM");
        jace.setItemQuantity(1);
        jace.setDetail("Magic Origins");
        jace.setType("MTG");
        jace.setStorageLocation(originsBinderId);

        land.setItemName("Fire-Lit Thicket");
        land.setItemNumber(29);
        land.setItemCondition("NM");
        land.setItemQuantity(1);
        land.setDetail("Zendikar Expeditions");
        land.setType("MTG");
        land.setStorageLocation(originsBinderId);

        itemRepository.save(xmen);
        itemRepository.save(avengers);
        itemRepository.save(jace);
        itemRepository.save(land);

        logger.info("Default items added.");

    }
}
