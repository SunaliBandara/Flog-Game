/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.service;

import static com.nsbm.common.CurrentPlay.currentRound;
import static com.nsbm.common.CurrentPlay.getPLAYER_ROUND_STATISTICS;
import static com.nsbm.common.ResponseResult.ERROR;
import static com.nsbm.common.ResponseResult.SUCCESS;
import com.nsbm.entity.PlayerStatistics;
import com.nsbm.entity.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Lakshitha
 */
@Path("/WordService")
public class WordService {

    private final static int INITIAL_LIMIT = 2;
    @Context
    HttpSession httpSession;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getInitialLetters")
    public String getInitialLetters(Player player) {

        Random r = new Random();
        String characters = new String();

        for (int i = 0; i < INITIAL_LIMIT; i++) {
            char c = (char) (r.nextInt(26) + 'a');
            characters = characters.concat(String.valueOf(c));
        }

        PlayerStatistics statistics = new PlayerStatistics();
        statistics.setInitialLetters(characters);

        Map<Integer, Map<Player, PlayerStatistics>> playerRoundStatistics = getPLAYER_ROUND_STATISTICS();
        Map<Player, PlayerStatistics> playerStatistics = playerRoundStatistics.get(currentRound);
        if (playerStatistics == null) {
            playerStatistics = new HashMap<Player, PlayerStatistics>();
            playerRoundStatistics.put(currentRound, playerStatistics);
        }
        playerStatistics.put(player, statistics);
        return characters;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getLetters/{requiredNumber}")
    public String getLetters(Player player, @PathParam("requiredNumber") int requiredNumber) {
        Random r = new Random();
        String characters = new String();

        for (int i = 0; i < requiredNumber; i++) {
            char c = (char) (r.nextInt(26) + 'a');
            characters = characters.concat(String.valueOf(c));
        }

        Map<Integer, Map<Player, PlayerStatistics>> playerRoundStatistics = getPLAYER_ROUND_STATISTICS();
        Map<Player, PlayerStatistics> playerStatistics = playerRoundStatistics.get(currentRound);

        PlayerStatistics p = playerStatistics.get(player);
        p.setPickedLetters(characters);
        return characters;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{word}")
    public String addWord(Player player, @PathParam("word") String word) {
        try {
            String checkUpURL = "http://words.bighugelabs.com/api/2/5e734d39c06e2274162ce24b1c0e3404/" + word + "/json";
            URL url = new URL(checkUpURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader br = null;
            String output = null;
            try {
                br = new BufferedReader(new InputStreamReader(
                        (connection.getInputStream())));
                output = br.readLine();
            } catch (IOException ex) {
                Logger.getLogger(WordService.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(WordService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           
            Map<Integer, Map<Player, PlayerStatistics>> playerRoundStatistics = getPLAYER_ROUND_STATISTICS();
            Map<Player, PlayerStatistics> playerStatistics = playerRoundStatistics.get(currentRound);

            PlayerStatistics p = playerStatistics.get(player);
            p.setWord(word);
            System.out.println(playerRoundStatistics);
            return SUCCESS;
        } catch (MalformedURLException ex) {
            Logger.getLogger(WordService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WordService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ERROR;
    }
}
