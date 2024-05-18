package com.example.travelor.service;

import com.example.travelor.bean.Canteen;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CanteenService {

    // http://10.0.2.2:8081/ 用于开发环境。10.0.2.2 为模拟器运行环境地址（比如运行模拟器的电脑）
    // http://aaapeng.tpddns.cn:8081/ 用于生产环境。aaapeng.tpddns.cn:8081 为服务器 API 地址，
    String BASE_URL = "http://aaapeng.tpddns.cn:8081/";

    interface GetService {
        @GET("canteen/{canteenName}")
        Call<Canteen> getCanteen(@Path("canteenName") String canteenName);
    }

    interface GetAllService {
        @GET("canteen/all")
        Call<List<Canteen>> getAllCanteen();
    }

}
