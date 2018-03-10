/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.maven.desafio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Desafio
 */
public class Main {

    public static void main(String[] args) {
        String response = "";
        try {
            response = getInformation();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Response: " + response);
        DesafioImplementation desafio = new DesafioImplementation(response);
        System.out.println("Id: " + desafio.getId());
        System.out.println("Name: " + desafio.getName());
        System.out.println("Description: " + desafio.getDescription());
        System.out.println("ThumbnailURL: " + desafio.getThumbnailURL());
        System.out.println("URLs:");
        for (String url : desafio.getURLs()) {
            System.out.println(url);
        }
    }

    //TODO: a decente API usage would be better
    private static String getInformation() throws Exception {
        String stringUrl = Constants.BASE_URL
                + Constants.CHARACTER_BY_ID_PATH.replace("{characterId}", Constants.BLACK_PANTHER_ID)
                + "?ts=1"
                + "&apikey=" + Constants.API_PUBLIC_KEY
                + "&hash=" + md5("1" + Constants.API_PRIVATE_KEY + Constants.API_PUBLIC_KEY);

        URL url = new URL(stringUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            // TODO: make better error treatment
            System.out.println("Erro tring to get informatios");
            return "";
        }

        StringBuilder response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response.toString();
    }
    
    private static String md5(String toMd5) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(toMd5.getBytes(), 0, toMd5.length());
        return DatatypeConverter.printHexBinary(messageDigest.digest()).toLowerCase();
    }
}
