package com.suracki.collector.external.dto;

import java.util.Map;

public class CardFace {
    Map<String, String> image_uris;

    public Map<String, String> getImage_uris() {
        return image_uris;
    }

    public void setImage_uris(Map<String, String> image_uris) {
        this.image_uris = image_uris;
    }
}
