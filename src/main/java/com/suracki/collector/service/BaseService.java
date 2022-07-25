package com.suracki.collector.service;

import com.suracki.collector.external.Scryfall;
import com.suracki.collector.repository.ItemRepository;
import com.suracki.collector.repository.LocationRepository;
import com.suracki.collector.repository.MtgCardRepository;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class BaseService {

    ItemRepository itemRepository;
    LocationRepository locationRepository;
    MtgCardRepository mtgCardRepository;
    Scryfall scryfall;

    public BaseService(ItemRepository itemRepository, LocationRepository locationRepository,
                       MtgCardRepository mtgCardRepository) {
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.mtgCardRepository = mtgCardRepository;
        scryfall = new Scryfall();
    }

    protected void addTypes(Model model) {
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
    }

}
