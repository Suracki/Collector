package com.suracki.collector.external;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suracki.collector.external.dto.ScryfallCard;
import com.suracki.collector.external.retrofit.ScryfallRetro;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Locale;

@Service
public class Scryfall {

    private Logger logger = LoggerFactory.getLogger(Scryfall.class);

    private Gson gson = new GsonBuilder().setLenient().create();

    public ScryfallCard getCardInfo(String set, int id) {
        logger.info("Scryfall getCardInfo called for [" + set + "] #" + id);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.scryfall.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        ScryfallRetro scryfallRetro = retrofit.create(ScryfallRetro.class);
        Call<ScryfallCard> callSync = scryfallRetro.getCardInfo(set.toLowerCase(Locale.ROOT), id);

        try {
            Response<ScryfallCard> response = callSync.execute();
            ScryfallCard scryfallCard = response.body();
            logger.info("Card info loaded for \"" + scryfallCard.getName() + "\"");

            System.out.println("Card JSON: ");
            System.out.println(gson.toJson(scryfallCard));
            return scryfallCard;
        }
        catch (IOException e) {
        logger.error("addToVisitedLocations external call failed: " + e);
        return null;
    }
    }


}
