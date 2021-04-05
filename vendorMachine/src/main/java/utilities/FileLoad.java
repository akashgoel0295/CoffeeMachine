package utilities;

import com.google.gson.Gson;
import models.BeverageRequest;
import models.RechargeRequest;
import models.Request;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FileLoad {
    public Request getRequestPojo(String fileName) {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream("/" + fileName)));
        Request request = new Gson().fromJson(br, Request.class);
        return request;
    }

    public RechargeRequest getRechargeRequest(String fileName) {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream("/" + fileName)));
        RechargeRequest rechargeRequest = new Gson().fromJson(br, RechargeRequest.class);
        return rechargeRequest;
    }

    public BeverageRequest getBeverageRequest(String fileName) {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream("/" + fileName)));
        BeverageRequest beverageRequest = new Gson().fromJson(br, BeverageRequest.class);
        return beverageRequest;
    }
}
