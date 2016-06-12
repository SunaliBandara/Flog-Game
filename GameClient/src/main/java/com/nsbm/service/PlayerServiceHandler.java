/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.service;

import com.google.gson.Gson;
import static com.nsbm.common.CommonUtil.setModelData;
import com.nsbm.common.PlayerStatus;
import com.nsbm.common.CommonData;
import static com.nsbm.common.CommonData.ADD_PLAYER;
import static com.nsbm.common.CommonData.BROADCAST;
import static com.nsbm.common.CommonData.GET;
import static com.nsbm.common.CommonData.GET_PLAYERS;
import static com.nsbm.common.CommonData.GET_ROUND_COMPLETED_PLAYERS;
import static com.nsbm.common.CommonData.PLAYER_CLASS;
import static com.nsbm.common.CommonData.PLAYER_JOIN_BROADCAST;
import static com.nsbm.common.CommonData.PLAYER_JOIN_LISTEN;
import static com.nsbm.common.CommonData.POST;
import static com.nsbm.common.CommonData.ROUND_COMPLETION_BROADCAST;
import static com.nsbm.common.CommonData.ROUND_COMPLETION_LISTEN;
import static com.nsbm.common.CommonData.currentRound;
import static com.nsbm.common.CommonData.username;
import static com.nsbm.common.CommonUtil.setRoundCompletedModelData;
import com.nsbm.entity.Player;
import com.nsbm.view.Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
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
    private static JLabel label;
    private static int counter = 10;
    private static Timer timer = null;
    private static JFrame frame = null;

    public static void setModelReference(DefaultListModel<String> model) {
        PlayerServiceHandler.model = model;
    }

    public static void setLabelReference(JLabel label) {
        PlayerServiceHandler.label = label;
    }

    public static void setFrameReference(JFrame frame) {
        PlayerServiceHandler.frame = frame;
    }

    public static String addPlayer(String playerName, String playerPassword) {
        String output = null;
        try {
            HttpURLConnection connection = new FactoryServiceHandler().getServiceConnection(PLAYER_CLASS, ADD_PLAYER, POST);
            sendOutput(playerName, connection);

            output = getInput(connection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return output;
    }

    public static Player[] getAllPlayers() {
        Player[] playerList = null;
        try {
            HttpURLConnection connection = new FactoryServiceHandler().getServiceConnection(PLAYER_CLASS, GET_PLAYERS, GET);
            String output = getInput(connection);
            Gson parser = new Gson();
            playerList = parser.fromJson(output, Player[].class);

        } catch (IOException e) {
            System.out.println(e);
        }
        return playerList;
    }

    public static String[] getRoundCompletedPlayers() {
        String[] playerList = null;
        try {
            HttpURLConnection connection = new FactoryServiceHandler().getServiceConnection(PLAYER_CLASS, GET_ROUND_COMPLETED_PLAYERS, GET);
            String output = getInput(connection);
            Gson parser = new Gson();
            playerList = parser.fromJson(output, String[].class);
        } catch (IOException e) {
            System.out.println(e);
        }
        return playerList;
    }

    public static void notifyPlayerJoin() {
        String output = null;
        try {
            HttpURLConnection connection = new FactoryServiceHandler().getServiceConnection(BROADCAST, PLAYER_JOIN_BROADCAST, POST);
            String input = CommonData.username;
            OutputStream os = connection.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            output = getInput(connection);
            System.out.println(output);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void listenToJoinEvent() {
        Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
        WebTarget target = client.target(CommonData.IP + BROADCAST + PLAYER_JOIN_LISTEN);

        EventInput eventInput = target.request().get(EventInput.class);
        while (!eventInput.isClosed()) {
            final InboundEvent inboundEvent = eventInput.read();
            if (CommonData.playerStatus == PlayerStatus.PLAYING) {
                break;
            }
            if (inboundEvent == null) {
                break;
            }
            setModelData(inboundEvent.readData(String.class), model);
        }
        System.out.println("Done");
    }

    public static void notifyRoundCompletion() {
        String output = null;
        try {
            System.out.println(username + " notifing completion");
            HttpURLConnection connection = new FactoryServiceHandler().getServiceConnection(BROADCAST, ROUND_COMPLETION_BROADCAST, POST);
            sendOutput(username, connection);
            output = getInput(connection);
//            System.out.println(username + "<<" +output);
//            if (output.equals("starting round " + currentRound) && currentRound==1) {
//                CommonData.isLastPlayer = true;
//                timer = new Timer(1000, new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        if (counter == 0) {
//                            timer.stop();
//                            frame.dispose();
//                            Game game = new Game();
//                            game.setVisible(true);
//                            counter = 10;
//                        } else {
//                            label.setText(String.valueOf(counter));
//                            counter--;
//                        }
//                    }
//                });
//                timer.setInitialDelay(0);
//                timer.start(); 
//            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void listenToRoundCompletionEvent() {
        System.out.println("Starting Listener");
        Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
        WebTarget target = client.target(CommonData.IP + BROADCAST + ROUND_COMPLETION_LISTEN);

        EventInput eventInput = target.request().get(EventInput.class);
        while (!eventInput.isClosed()) {
            System.out.println("Listening");
            final InboundEvent inboundEvent = eventInput.read();
            if (inboundEvent == null) {
                System.out.println("Listener Stopped");
                break;
            }
            System.out.println(username + " > " + inboundEvent.readData(String.class));

            counter = 10;
            if (inboundEvent.readData(String.class).equals("roundEnd")) {
                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (counter == 0) {
                            timer.stop();
                            frame.dispose();
                            Game game = new Game();
                            game.setVisible(true);
                        } else {
                            label.setText(String.valueOf(counter));
                            counter--;
                        }
                    }
                });
                timer.setInitialDelay(0);
                timer.start();
                System.out.println(username + " stopped listening");
                break;
            } else {
                setRoundCompletedModelData(inboundEvent.readData(String.class), model);
            }
        }
    }

    private static void sendOutput(String username, HttpURLConnection connection) {
        try {
            Player player = new Player();
            player.setUsername(username);
            String input = new Gson().toJson(player);
            OutputStream os = connection.getOutputStream();
            os.write(input.getBytes());
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(WordServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String getInput(HttpURLConnection connection) {
        BufferedReader br = null;
        String output = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));
            output = br.readLine();
            return output;
        } catch (IOException ex) {
            Logger.getLogger(WordServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(WordServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return output;
    }
}
