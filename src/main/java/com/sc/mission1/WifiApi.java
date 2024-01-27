package com.sc.mission1;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;

import static com.sc.mission1.Databases.insertWifi;


public class WifiApi {
    private static OkHttpClient client = new OkHttpClient();
    private static String ApiUrl = "http://openapi.seoul.go.kr:8088/4279564956686a6b3130394f50465061/json/TbPublicWifiInfo/";

    public static int WifiCount() throws IOException {
        int count = 0;

        URL url = new URL(ApiUrl + "1/1");

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String jsonData = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
            JsonObject publicWifiInfo = jsonObject.getAsJsonObject("TbPublicWifiInfo");
            count = publicWifiInfo.get("list_total_count").getAsInt();

            System.out.println("와이파이 개수 : " + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int getPublicWifiJson() throws IOException {
        int totalCnt = WifiCount();
        int start = 1, end = 1;
        int count = 0;
        Gson gson = new Gson();

        try {
            for (int i = 0; i <= totalCnt / 1000; i++) {
                start = 1 + (1000 * i);
                end = (i + 1) * 1000;

                URL url = new URL(ApiUrl + start + "/" + end);

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }
                    String jsonData = response.body().string();
                    WifiInfo wifiInfo = gson.fromJson(jsonData, WifiInfo.class);
                    count += insertWifi(wifiInfo.getRow());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

}
