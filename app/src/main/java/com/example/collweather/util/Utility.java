package com.example.collweather.util;

import android.text.TextUtils;

import com.example.collweather.db.City;
import com.example.collweather.db.County;
import com.example.collweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    //解析服务器返回的省级数据
    public static boolean handleProvinceResponse(String response){
        try {
            JSONArray allProvinces = new JSONArray(response);
            for (int i = 0;i<allProvinces.length() ; i++){
                JSONObject provinceObject = allProvinces.getJSONObject(i);
                Province province = new Province();
                province.setProvinceName(provinceObject.getString("name"));
                province.setProvinceCode(provinceObject.getInt("id"));
                province.save();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //解析服务器返回的市级数据
    public static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0;i<allCities.length() ; i++){
                    JSONObject citiesObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(citiesObject.getString("name"));
                    city.setCityCode(citiesObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //解析服务器返回的县级数据
    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounty = new JSONArray(response);
                for (int i = 0;i<allCounty.length() ; i++){
                    JSONObject CountyObject = allCounty.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(CountyObject.getString("name"));
                    county.setWeatherId(CountyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
