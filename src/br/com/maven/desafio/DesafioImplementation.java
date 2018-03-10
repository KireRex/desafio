/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.maven.desafio;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Desafio
 */
//TODO: A decent API would be better than this JSON access
public class DesafioImplementation implements Desafio {

    JSONObject json;

    public DesafioImplementation(JSONObject json) {
        this.json = json;
    }

    public DesafioImplementation(String jsonString) {
        json = new JSONObject(jsonString);
    }

    @Override
    public Integer getId() {
        return getFirstResult().getInt("id");
    }

    @Override
    public String getName() {
        return getFirstResult().getString("name");
    }

    @Override
    public String getDescription() {
        return getFirstResult().getString("description");
    }

    @Override
    public String getThumbnailURL() {
        return getFirstResult().getJSONObject("thumbnail").getString("path");
    }

    @Override
    public String[] getURLs() {
        List<String> urls = new ArrayList<>();
        JSONArray array = getFirstResult().getJSONArray("urls");
        for (int i = 0; i < array.length(); i++) {
            urls.add(array.getJSONObject(i).getString("url"));
        }
        String[] resultArray = new String[urls.size()];
        resultArray = urls.toArray(resultArray);
        return resultArray;
    }

    private JSONObject getFirstResult() {
        return json.getJSONObject("data").getJSONArray("results").getJSONObject(0);
    }
}
