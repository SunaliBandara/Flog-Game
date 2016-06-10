/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.service;

import com.google.gson.Gson;
import static com.nsbm.common.CommonUtil.setModelData;
import com.nsbm.common.PlayerStatus;
import com.nsbm.common.UserData;
import static com.nsbm.common.UserData.ADD_PLAYER;
import static com.nsbm.common.UserData.BROADCAST;
import static com.nsbm.common.UserData.GET;
import static com.nsbm.common.UserData.GET_PLAYERS;
import static com.nsbm.common.UserData.PLAYER_CLASS;
import static com.nsbm.common.UserData.PLAYER_JOIN_BROADCAST;
import static com.nsbm.common.UserData.PLAYER_JOIN_LISTEN;
import static com.nsbm.common.UserData.POST;
import com.nsbm.entity.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import javax.swing.DefaultListModel;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

/**
 *
 * @author Lakshitha
 */
public class PlayerServiceHandler {

    private static DefaultListModel<String> model = null;

    public static void setModelReference(DefaultListModel<String> model) {
        PlayerServiceHandler.model = model;
    }

    public static String addPlayer(String playerName, String playerPassword) {
        String output = null;
        try {
            HttpURLConnection connection = new ServiceFactory().getServiceConnection(PLAYER_CLASS, ADD_PLAYER, POST);
            Player player = new Player();
            player.setUsername(playerName);
            player.setPassword(playerPassword);
            String input = new Gson().toJson(player);
            OutputStream os = connection.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            output = br.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return output;
    }

    public static Player[] getAllPlayers() {
        Player[] playerList = null;
        try {
            HttpURLConnection conn = new ServiceFactory().getServiceConnection(PLAYER_CLASS, GET_PLAYERS, GET);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output = br.readLine();
            Gson parser = new Gson();
            playerList = parser.fromJson(output, Player[].class);

        } catch (IOException e) {
            System.out.println(e);
        }
        return playerList;
    }

    public static void notifyPlayerJoin() {
        String output = null;
        try {
            HttpURLConnection conn = new ServiceFactory().getServiceConnection(BROADCAST, PLAYER_JOIN_BROADCAST, POST);
            String input = UserData.username;
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            output = br.readLine();
            System.out.println(output);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void listendToJoinEvent() {
        Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
        WebTarget target = client.target(UserData.IP + BROADCAST + PLAYER_JOIN_LISTEN);

        EventInput eventInput = target.request().get(EventInput.class);
        while (!eventInput.isClosed()) {
            final InboundEvent inboundEvent = eventInput.read();
            if (inboundEvent == null) {
                break;
            }
            if (UserData.playerStatus == PlayerStatus.PLAYING) {
                break;
            }
            setModelData(inboundEvent.readData(String.class),model);
        }
        System.out.println("Playing");
    }

}
