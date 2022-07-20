package com.suracki.collector.service;

import com.suracki.collector.repository.ItemRepository;
import com.suracki.collector.repository.LocationRepository;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class BaseService {

    ItemRepository itemRepository;
    LocationRepository locationRepository;

    public BaseService(ItemRepository itemRepository, LocationRepository locationRepository) {
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
    }

    protected void addTypes(Model model) {
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
    }

}
