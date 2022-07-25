package com.suracki.collector.service;

import com.suracki.collector.domain.Item;
import com.suracki.collector.domain.Location;
import com.suracki.collector.domain.MtgCard;
import com.suracki.collector.external.dto.ScryfallCard;
import com.suracki.collector.repository.ItemRepository;
import com.suracki.collector.repository.LocationRepository;
import com.suracki.collector.repository.MtgCardRepository;
import com.suracki.collector.security.SessionDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class CollectionService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(CollectionService.class);


    public CollectionService(ItemRepository itemRepository, LocationRepository locationRepository,
                             MtgCardRepository mtgCardRepository) {
        super(itemRepository, locationRepository, mtgCardRepository);
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

        jace.setItemName("Jace, Vryn's Prodigy // Jace, Telepath Unbound");
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
        SessionDetails sessionDetails = getSession();
        sessionDetails.setLastViewedType(type);
        sessionDetails.setLastPageNumber(0);
        return viewPaged(model);
    }

    public String viewPaged(Model model) {
        super.addTypes(model);
        SessionDetails sessionDetails = getSession();
        String type = sessionDetails.getLastViewedType();
        Integer page = sessionDetails.getLastPageNumber() + 1;

        List<Item> items = itemRepository.findByType(type);
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
            return "guest/viewPaged";
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
        return "guest/viewPaged";

    }

    public String viewPagedBack(Model model) {
        super.addTypes(model);
        SessionDetails sessionDetails = getSession();
        String type = sessionDetails.getLastViewedType();
        Integer page = sessionDetails.getLastPageNumber() - 1;

        List<Item> items = itemRepository.findByType(type);
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
            return "guest/viewPaged";
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
        return "guest/viewPaged";

    }

    public String viewByName(String name, Model model) {
        super.addTypes(model);
        SessionDetails sessionDetails = getSession();
        model.addAttribute("collection", itemRepository.findByName(name));
        return "guest/view";
    }

    public String viewByDetail(String detail, Model model) {
        super.addTypes(model);
        SessionDetails sessionDetails = getSession();
        model.addAttribute("collection", itemRepository.findByDetail(detail));
        return "guest/view";
    }

    public String cardDetails(Model model, String set_code, String collectors_number) {
        MtgCard mtgCard = mtgCardRepository.findBySetAndCollectorNumber(set_code, collectors_number);
        SessionDetails sessionDetails = getSession();
        if (mtgCard == null) {
            ScryfallCard scryfallCard = scryfall.getCardInfo(set_code, Integer.parseInt(collectors_number));
            mtgCard = new MtgCard(scryfallCard);
            mtgCardRepository.save(mtgCard);
        }
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
        model.addAttribute("mtgCard", mtgCard);
        return "guest/cardDetails";
    }
}
