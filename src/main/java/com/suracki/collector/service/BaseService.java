package com.suracki.collector.service;

import com.suracki.collector.datamonitor.Monitor;
import com.suracki.collector.external.Scryfall;
import com.suracki.collector.repository.ItemRepository;
import com.suracki.collector.repository.LocationRepository;
import com.suracki.collector.repository.MtgCardRepository;
import com.suracki.collector.security.SessionDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.*;

public abstract class BaseService {

    ItemRepository itemRepository;
    LocationRepository locationRepository;
    MtgCardRepository mtgCardRepository;
    Scryfall scryfall;

    protected static Map<String, SessionDetails> sessions;

    private static int sessionCacheSize = 1000;


    public BaseService(ItemRepository itemRepository, LocationRepository locationRepository,
                       MtgCardRepository mtgCardRepository) {
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.mtgCardRepository = mtgCardRepository;
        sessions = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                return size() > sessionCacheSize;
            }
        };
        System.out.println("Session Cache: " + sessionCacheSize);
        scryfall = new Scryfall();
    }



    protected SessionDetails getSession() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        if (sessions.get(sessionId) == null ){
            SessionDetails session = new SessionDetails();
            session.setLastPageNumber(0);
            sessions.put(sessionId, session);
        }
        System.out.println("SESSIONS STORED: " + sessions.size());
        return sessions.get(sessionId);
    }

    protected void addTypes(Model model) {
        model.addAttribute("types", new ArrayList<>(new LinkedHashSet<String>(itemRepository.getTypes())));
    }

}
