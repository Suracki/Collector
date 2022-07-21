package com.suracki.collector.external.dto;

import java.util.List;
import java.util.Map;

public class MtgCard {
    String object;
    String id;
    String oracle_id;
    List<Integer> multiverse_ids;
    int mtgo_id;
    int mtgo_foil_id;
    int tcgplayer_id;
    int cardmarket_id;
    String name;
    String lang;
    String released_at;
    String uri;
    String scryfall_uri;
    String layout;
    Boolean hires_image;
    String image_status;
    Map<String, String> image_uris;
    String mana_cost;
    Double cmc;
    String type_line;
    String oracle_test;
    String power;
    String toughness;
    List<String> colors;
    List<String> color_identity;
    List<String> keywords;
    Map<String, String> legalities;
    List<String> games;
    String reserved;
    String foil;
    String nonfoil;
    List<String> finishes;
    String oversized;
    String promo;
    String reprint;
    String variation;
    String set_id;
    String set;
    String set_name;
    String set_type;
    String set_uri;
    String set_search_uri;
    String scryfall_set_uri;
    String rulings_uri;
    String prints_search_uri;
    String collector_number;
    Boolean digital;
    String rarity;
    String flavor_text;
    String card_back_id;
    String artist;
    List<String> artist_ids;
    String illustration_id;
    String border_color;
    String frame;
    Boolean full_art;
    Boolean textless;
    Boolean booster;
    Boolean story_spotlight;
    int edhrec_rank;
    int penny_rank;
    Map<String, Double> prices;
    Map<String, String> related_uris;
    Map<String, String> purchase_uris;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOracle_id() {
        return oracle_id;
    }

    public void setOracle_id(String oracle_id) {
        this.oracle_id = oracle_id;
    }

    public List<Integer> getMultiverse_ids() {
        return multiverse_ids;
    }

    public void setMultiverse_ids(List<Integer> multiverse_ids) {
        this.multiverse_ids = multiverse_ids;
    }

    public int getMtgo_id() {
        return mtgo_id;
    }

    public void setMtgo_id(int mtgo_id) {
        this.mtgo_id = mtgo_id;
    }

    public int getMtgo_foil_id() {
        return mtgo_foil_id;
    }

    public void setMtgo_foil_id(int mtgo_foil_id) {
        this.mtgo_foil_id = mtgo_foil_id;
    }

    public int getTcgplayer_id() {
        return tcgplayer_id;
    }

    public void setTcgplayer_id(int tcgplayer_id) {
        this.tcgplayer_id = tcgplayer_id;
    }

    public int getCardmarket_id() {
        return cardmarket_id;
    }

    public void setCardmarket_id(int cardmarket_id) {
        this.cardmarket_id = cardmarket_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getReleased_at() {
        return released_at;
    }

    public void setReleased_at(String released_at) {
        this.released_at = released_at;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getScryfall_uri() {
        return scryfall_uri;
    }

    public void setScryfall_uri(String scryfall_uri) {
        this.scryfall_uri = scryfall_uri;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public Boolean getHires_image() {
        return hires_image;
    }

    public void setHires_image(Boolean hires_image) {
        this.hires_image = hires_image;
    }

    public String getImage_status() {
        return image_status;
    }

    public void setImage_status(String image_status) {
        this.image_status = image_status;
    }

    public Map<String, String> getImage_uris() {
        return image_uris;
    }

    public void setImage_uris(Map<String, String> image_uris) {
        this.image_uris = image_uris;
    }

    public String getMana_cost() {
        return mana_cost;
    }

    public void setMana_cost(String mana_cost) {
        this.mana_cost = mana_cost;
    }

    public Double getCmc() {
        return cmc;
    }

    public void setCmc(Double cmc) {
        this.cmc = cmc;
    }

    public String getType_line() {
        return type_line;
    }

    public void setType_line(String type_line) {
        this.type_line = type_line;
    }

    public String getOracle_test() {
        return oracle_test;
    }

    public void setOracle_test(String oracle_test) {
        this.oracle_test = oracle_test;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getColor_identity() {
        return color_identity;
    }

    public void setColor_identity(List<String> color_identity) {
        this.color_identity = color_identity;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public Map<String, String> getLegalities() {
        return legalities;
    }

    public void setLegalities(Map<String, String> legalities) {
        this.legalities = legalities;
    }

    public List<String> getGames() {
        return games;
    }

    public void setGames(List<String> games) {
        this.games = games;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getFoil() {
        return foil;
    }

    public void setFoil(String foil) {
        this.foil = foil;
    }

    public String getNonfoil() {
        return nonfoil;
    }

    public void setNonfoil(String nonfoil) {
        this.nonfoil = nonfoil;
    }

    public List<String> getFinishes() {
        return finishes;
    }

    public void setFinishes(List<String> finishes) {
        this.finishes = finishes;
    }

    public String getOversized() {
        return oversized;
    }

    public void setOversized(String oversized) {
        this.oversized = oversized;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getReprint() {
        return reprint;
    }

    public void setReprint(String reprint) {
        this.reprint = reprint;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public String getSet_id() {
        return set_id;
    }

    public void setSet_id(String set_id) {
        this.set_id = set_id;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getSet_name() {
        return set_name;
    }

    public void setSet_name(String set_name) {
        this.set_name = set_name;
    }

    public String getSet_type() {
        return set_type;
    }

    public void setSet_type(String set_type) {
        this.set_type = set_type;
    }

    public String getSet_uri() {
        return set_uri;
    }

    public void setSet_uri(String set_uri) {
        this.set_uri = set_uri;
    }

    public String getSet_search_uri() {
        return set_search_uri;
    }

    public void setSet_search_uri(String set_search_uri) {
        this.set_search_uri = set_search_uri;
    }

    public String getScryfall_set_uri() {
        return scryfall_set_uri;
    }

    public void setScryfall_set_uri(String scryfall_set_uri) {
        this.scryfall_set_uri = scryfall_set_uri;
    }

    public String getRulings_uri() {
        return rulings_uri;
    }

    public void setRulings_uri(String rulings_uri) {
        this.rulings_uri = rulings_uri;
    }

    public String getPrints_search_uri() {
        return prints_search_uri;
    }

    public void setPrints_search_uri(String prints_search_uri) {
        this.prints_search_uri = prints_search_uri;
    }

    public String getCollector_number() {
        return collector_number;
    }

    public void setCollector_number(String collector_number) {
        this.collector_number = collector_number;
    }

    public Boolean getDigital() {
        return digital;
    }

    public void setDigital(Boolean digital) {
        this.digital = digital;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getFlavor_text() {
        return flavor_text;
    }

    public void setFlavor_text(String flavor_text) {
        this.flavor_text = flavor_text;
    }

    public String getCard_back_id() {
        return card_back_id;
    }

    public void setCard_back_id(String card_back_id) {
        this.card_back_id = card_back_id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public List<String> getArtist_ids() {
        return artist_ids;
    }

    public void setArtist_ids(List<String> artist_ids) {
        this.artist_ids = artist_ids;
    }

    public String getIllustration_id() {
        return illustration_id;
    }

    public void setIllustration_id(String illustration_id) {
        this.illustration_id = illustration_id;
    }

    public String getBorder_color() {
        return border_color;
    }

    public void setBorder_color(String border_color) {
        this.border_color = border_color;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public Boolean getFull_art() {
        return full_art;
    }

    public void setFull_art(Boolean full_art) {
        this.full_art = full_art;
    }

    public Boolean getTextless() {
        return textless;
    }

    public void setTextless(Boolean textless) {
        this.textless = textless;
    }

    public Boolean getBooster() {
        return booster;
    }

    public void setBooster(Boolean booster) {
        this.booster = booster;
    }

    public Boolean getStory_spotlight() {
        return story_spotlight;
    }

    public void setStory_spotlight(Boolean story_spotlight) {
        this.story_spotlight = story_spotlight;
    }

    public int getEdhrec_rank() {
        return edhrec_rank;
    }

    public void setEdhrec_rank(int edhrec_rank) {
        this.edhrec_rank = edhrec_rank;
    }

    public int getPenny_rank() {
        return penny_rank;
    }

    public void setPenny_rank(int penny_rank) {
        this.penny_rank = penny_rank;
    }

    public Map<String, Double> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, Double> prices) {
        this.prices = prices;
    }

    public Map<String, String> getRelated_uris() {
        return related_uris;
    }

    public void setRelated_uris(Map<String, String> related_uris) {
        this.related_uris = related_uris;
    }

    public Map<String, String> getPurchase_uris() {
        return purchase_uris;
    }

    public void setPurchase_uris(Map<String, String> purchase_uris) {
        this.purchase_uris = purchase_uris;
    }
}
