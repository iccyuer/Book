package com.example.book.books.net;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Admin on 2017/5/18.
 */

public interface YLService {
    @GET("")
    public Observable<String> doPay();

    @GET("sim/getacptn/")
    public Call<String> doPay1();

    @GET("/")
    public Call<String> doPay2();
}
