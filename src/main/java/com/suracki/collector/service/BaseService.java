package com.suracki.collector.service;

import com.suracki.collector.external.Scryfall;
import com.suracki.collector.repository.ItemRepository;
import com.suracki.collector.repository.LocationRepository;
import com.suracki.collector.repository.MtgCardRepository;
import com.suracki.collector.security.SessionDetails;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class BaseService {

    ItemRepository itemRepository;
    LocationRepository locationRepository;
    MtgCardRepository mtgCardRepository;
    Scryfall scryfall;
    protected Map<String, SessionDetails> sessions;

    public BaseService(ItemRepository itemRepository, LocationRepository locationRepository,
                       MtgCardRepository mtgCardRepository) {
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.mtgCardRepository = mtgCardRepository;
        sessions = new HashMap<>();
        scryfall = new Scryfall();
    }

    protected SessionDetails getSession() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        if (sessions.get(sessionId) == null ){
            sessions.put(sessionId, new SessionDetails());
        }
        return sessions.get(sessionId);
    }

    protected void addTypes(Model model) {
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
    }

}
