package com.example.youtube.youtubeapp.api;

import com.example.youtube.youtubeapp.model.Resultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeService {

//    @GET("search/{param1})
//    Call<> recupearVideos(@Path("param1") String param1, String param2);

    /*
    https://www.googleapis.com/youtube/v3/
    search
    ?part=snippet
    &order=date
    &maxResults=20
    &key=AIzaSyDz7uYd0sDWnAfiZBr4kvaa_cPt24FWezc
    &channelId=UCY8iijN1AkyDCh1Z9akcqUA
    &q=jogo
     */


    @GET("search")
    Call<Resultado> recupearVideos(@Query("part") String part,
                                   @Query("order") String order,
                                   @Query("maxResults") String maxResults,
                                   @Query("key") String key,
                                   @Query("channelId") String channelId,
                                   @Query("q") String q);
}
