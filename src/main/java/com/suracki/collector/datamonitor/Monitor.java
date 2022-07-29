package com.suracki.collector.datamonitor;

import com.suracki.collector.controller.HomeController;
import com.suracki.collector.domain.MtgCard;
import com.suracki.collector.external.Scryfall;
import com.suracki.collector.external.dto.ScryfallCard;
import com.suracki.collector.external.retrofit.ScryfallRetro;
import com.suracki.collector.repository.ItemRepository;
import com.suracki.collector.repository.MtgCardRepository;
import com.suracki.collector.security.SessionDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Monitor thread runs in the background, allows tasks to be scheduled to run
 * Currently set to update MTG card details weekly (for updated price info)
 */
public class Monitor extends Thread {

    private static final Logger logger = LogManager.getLogger(HomeController.class);
    private static final long trackingPollingInterval = TimeUnit.DAYS.toSeconds(7);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private boolean stop = false;
    protected static Map<String, SessionDetails> sessions;


    private MtgCardRepository mtgCardRepository;


    public Monitor(MtgCardRepository mtgCardRepository, Map<String, SessionDetails> sessions) {
        this.mtgCardRepository = mtgCardRepository;
        this.sessions = sessions;
        executorService.submit(this);
    }

    public void stopMonitor() {
        logger.info("Shutdown Thread called stopMonitor");
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
            updatePrices();
            logger.info("Price update completed.");


            try {
                logger.info("Tracker sleeping");
                TimeUnit.SECONDS.sleep(trackingPollingInterval);
            } catch (InterruptedException e) {
                break;
            }
        }

    }

    public void updatePrice(MtgCard mtgCard) {
        Scryfall scryfall = new Scryfall();
        ScryfallCard scryfallCard = scryfall.getCardInfo(mtgCard.getSet_code(), Integer.parseInt(mtgCard.getCollector_number()));
        mtgCard.setPrice(scryfallCard.getPrices().get("eur"));
        mtgCard.setPriceUpdateTime(LocalDateTime.now());
        mtgCardRepository.save(mtgCard);
    }

    private void updatePrices() {
        List<MtgCard> mtgCards = mtgCardRepository.findAll();
        if (mtgCards == null) {
            logger.info("Monitor found no card records.");
            return;
        }
        logger.info("Monitor found " + mtgCards.size() + " card records. Updating any card details older than 7 days.");
        Scryfall scryfall = new Scryfall();
        mtgCards.forEach( card -> {
            if (Duration.between(card.getPriceUpdateTime(), LocalDateTime.now()).toDays() > 7) {
                ScryfallCard scryfallCard = scryfall.getCardInfo(card.getSet_code(), Integer.parseInt(card.getCollector_number()));
                card.setPrice(scryfallCard.getPrices().get("eur"));
                card.setPriceUpdateTime(LocalDateTime.now());
                mtgCardRepository.save(card);
                logger.info("Updated card: " + card.getSet_code() + " " + card.getCollector_number());
            }
        });
    }
}
