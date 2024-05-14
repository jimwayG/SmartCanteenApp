package com.example.travelor.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Monitoring {

    String BASE_URL = "http://10.0.2.2:8080/"; // 10.0.2.2 为模拟器运行环境地址（比如运行模拟器的电脑）

    class CanteenMonitoring {

        private Integer capacity;
        private String name;
        private Integer peopleNum;
        private Double saturation;
        private String state;

        public Integer getCapacity() {
            return capacity;
        }

        public void setCapacity(Integer capacity) {
            this.capacity = capacity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPeopleNum() {
            return peopleNum;
        }

        public void setPeopleNum(Integer peopleNum) {
            this.peopleNum = peopleNum;
        }

        public Double getSaturation() {
            return saturation;
        }

        public void setSaturation(Double saturation) {
            this.saturation = saturation;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return "CanteenMonitoring{" +
                    "capacity=" + capacity +
                    ", name='" + name + '\'' +
                    ", peopleNum=" + peopleNum +
                    ", saturation=" + saturation +
                    ", state='" + state + '\'' +
                    '}';
        }
    }

    interface GetService {

        @GET("monitoring/{canteenName}")
        Call<CanteenMonitoring> getMonitoring(@Path("canteenName") String canteenName);

    }

}
