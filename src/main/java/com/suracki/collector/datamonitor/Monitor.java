package com.suracki.collector.datamonitor;

import com.suracki.collector.controller.HomeController;
import com.suracki.collector.domain.MtgCard;
import com.suracki.collector.external.Scryfall;
import com.suracki.collector.external.dto.ScryfallCard;
import com.suracki.collector.external.retrofit.ScryfallRetro;
import com.suracki.collector.repository.ItemRepository;
import com.suracki.collector.repository.MtgCardRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Monitor extends Thread {

    private static final Logger logger = LogManager.getLogger(HomeController.class);
    private static final long trackingPollingInterval = TimeUnit.HOURS.toSeconds(24);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private boolean stop = false;

    private LocalDateTime lastUpdate;

    private MtgCardRepository mtgCardRepository;


    public Monitor(MtgCardRepository mtgCardRepository) {
        this.mtgCardRepository = mtgCardRepository;
        executorService.submit(this);
    }

    public void stopMonitor() {
        stop = true;
        executorService.shutdownNow();
    }


    @Override
    public void run() {
        logger.info("Monitor thread initialized");
        while(true) {
            if(Thread.currentThread().isInterrupted() || stop) {
                logger.info("Monitor stopping");
                break;
            }
            logger.info("Requesting price update");
            //updatePrices();
            logger.info("Price update completed.");
            try {
                logger.info("Tracker sleeping");
                TimeUnit.SECONDS.sleep(trackingPollingInterval);
            } catch (InterruptedException e) {
                break;
            }
        }

    }

    private void updatePrices() {
        List<MtgCard> mtgCards = mtgCardRepository.findAll();
        if (mtgCards == null) {
            logger.info("Monitor found no card records.");
            return;
        }
        logger.info("Monitor found " + mtgCards.size() + " card records.");
        Scryfall scryfall = new Scryfall();
        mtgCards.forEach( card -> {
            ScryfallCard scryfallCard = scryfall.getCardInfo(card.getSet_code(), Integer.parseInt(card.getCollector_number()));
            card.setPrice(scryfallCard.getPrices().get("eur"));
            card.setPriceUpdateTime(LocalDateTime.now());
            mtgCardRepository.save(card);
        });
    }
}
