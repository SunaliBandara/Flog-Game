/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.service;

import com.nsbm.common.UserData;
import static com.nsbm.common.UserData.IP;
import static com.nsbm.common.UserData.id;
import static com.nsbm.common.UserData.username;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Lakshitha
 */
public class WordServiceHandler {

    private final static String WORDCLASS = "WordService/";

    public static String getInitialLetters() {
        String output = null;
        try {
            URL url = new URL(IP + WORDCLASS + "getInitialLetters");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"username\":\"" + username + "\",\"id\":\""+ id +"\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            output = br.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return output;
    }
    
    public static String getLetters(int required) {
        String output = null;
        try {
            URL url = new URL(IP + WORDCLASS + "getLetters/"+required);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"username\":\"" + username + "\",\"id\":\""+ id +"\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            output = br.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return output;
    }
    
    public static String addWord(String word) {
        String output = null;
        try {
            URL url = new URL(UserData.IP + WORDCLASS + word);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"username\":\"" + username + "\",\"id\":\""+ id +"\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            output = br.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return output;
    }
}
