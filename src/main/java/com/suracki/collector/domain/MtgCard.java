package com.suracki.collector.domain;

import com.suracki.collector.external.dto.ScryfallCard;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Table(name="MtgCard")
public class MtgCard {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int mtgCardId;

    String name;
    String image_uri;
    String set_code;
    String set_name;
    String collector_number;
    String rarity;
    Double price;
    String purchaseUri;
    LocalDateTime priceUpdateTime;

    public MtgCard() {
    }

    public MtgCard(ScryfallCard scryfallCard) {
        this.name = scryfallCard.getName();
        if (scryfallCard.getImage_uris() == null) {
            this.image_uri = "";
        }
        else {
            this.image_uri = scryfallCard.getImage_uris().get("normal");
        }
        this.set_code = scryfallCard.getSet();
        this.set_name = scryfallCard.getSet_name();
        this.collector_number = scryfallCard.getCollector_number();
        this.rarity = scryfallCard.getRarity();
        this.price = scryfallCard.getPrices().get("eur");
        this.purchaseUri = scryfallCard.getPurchase_uris().get("cardmarket");
        priceUpdateTime = LocalDateTime.now();
    }

    public int getMtgCardId() {
        return mtgCardId;
    }

    public void setMtgCardId(int mtgCardId) {
        this.mtgCardId = mtgCardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public String getSet_code() {
        return set_code.toUpperCase(Locale.ROOT);
    }

    public void setSet_code(String set_code) {
        this.set_code = set_code;
    }

    public String getSet_name() {
        return set_name;
    }

    public void setSet_name(String set_name) {
        this.set_name = set_name;
    }

    public String getCollector_number() {
        return collector_number;
    }

    public void setCollector_number(String collector_number) {
        this.collector_number = collector_number;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getPriceUpdateTime() {
        return priceUpdateTime;
    }

    public void setPriceUpdateTime(LocalDateTime priceUpdateTime) {
        this.priceUpdateTime = priceUpdateTime;
    }

    public String getPurchaseUri() {
        return purchaseUri;
    }

    public void setPurchaseUri(String purchaseUri) {
        this.purchaseUri = purchaseUri;
    }
}
