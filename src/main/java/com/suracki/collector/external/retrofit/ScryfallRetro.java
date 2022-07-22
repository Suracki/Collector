package com.suracki.collector.external.retrofit;

import com.suracki.collector.external.dto.ScryfallCard;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@Service
public interface ScryfallRetro {

    @GET("/cards/{set}/{id}")
    public Call<ScryfallCard> getCardInfo(@Path("set")String set, @Path("id") int id);

}
