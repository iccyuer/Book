package com.example.book.library.net;

/**
 * Created by Administractor on 2016/12/23.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2016/12/23.
 * <p>
 * 1.Thread
 * <p>
 * 2.start---线程池
 * <p>
 * 3.URL        参数1  String url
 * <p>
 * 4.HttpURLConnection 构建  流的读取
 * <p>
 * 5.gson的解析  参数2  Class<T>  type
 * <p>
 * 6.handler发消息
 * <p>
 * 7.参数 3  网络访问结束之后的回调动作（成功，失败）
 * <p>
 * 8.参数  4 get/post
 * 9.从回调接口抽取实体类型，只有abstract类才可以抽取泛型
 */
public class NetJsonUtil {

    public static <T> T getRetrofit(String baseUrl,Class<T> service) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return (T) retrofit.create(service);

    }
}
