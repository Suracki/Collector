package com.suracki.collector.external;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suracki.collector.external.dto.MtgCard;
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

@Service
public class Scryfall {

    private Logger logger = LoggerFactory.getLogger(Scryfall.class);

    private Gson gson = new GsonBuilder().setLenient().create();

    public MtgCard getCardInfo(String set, int id) {
        logger.info("Scryfall getCardInfo called for [" + set + "] #" + id);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.scryfall.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        ScryfallRetro scryfallRetro = retrofit.create(ScryfallRetro.class);
        Call<MtgCard> callSync = scryfallRetro.getCardInfo(set, id);

        try {
            Response<MtgCard> response = callSync.execute();
            MtgCard mtgCard = response.body();
            logger.info("Card info loaded for \"" + mtgCard.getName() + "\"");
            return mtgCard;
        }
        catch (IOException e) {
        logger.error("addToVisitedLocations external call failed: " + e);
        return null;
    }
    }


}
