/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.service;

import static com.nsbm.common.CommonData.DICTIONARY_LINK;
import static com.nsbm.common.CommonUtil.findPlayer;
import static com.nsbm.common.CommonUtil.getPlayerStatisticsFromPlayer;
import static com.nsbm.common.CommonUtil.getPoints;
import static com.nsbm.common.CommonUtil.getRandomConsonants;
import static com.nsbm.common.CommonUtil.getRandomVowels;
import static com.nsbm.common.CommonUtil.verifyWord;
import static com.nsbm.common.CurrentPlay.currentRound;
import static com.nsbm.common.CurrentPlay.getPLAYER_ROUND_STATISTICS;
import static com.nsbm.common.CurrentPlay.submitedWords;
import com.nsbm.common.PlayerStatus;
import com.nsbm.common.ResponseResult;
import static com.nsbm.common.ResponseResult.ERROR;
import static com.nsbm.common.ResponseResult.INCORRECT_WORD;
import static com.nsbm.common.ResponseResult.SUCCESS;
import com.nsbm.common.WordStatus;
import com.nsbm.entity.PlayerStatistics;
import com.nsbm.entity.Player;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Lakshitha
 */
@Path("/WordService")
public class WordService {

    private final static int INITIAL_LIMIT = 2;

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

        Player listPlayer = findPlayer(player.getUsername());
        
        PlayerStatistics statistics = new PlayerStatistics();
        statistics.setInitialLetters(characters);
        statistics.setWordStatus(WordStatus.NOT_SUBMITTED);

        Map<Integer, Map<Player, PlayerStatistics>> playerRoundStatistics = getPLAYER_ROUND_STATISTICS();
        Map<Player, PlayerStatistics> playerStatistics = playerRoundStatistics.get(currentRound);
        if (playerStatistics == null) {
            playerStatistics = new HashMap<Player, PlayerStatistics>();
            playerRoundStatistics.put(currentRound, playerStatistics);
        }
        playerStatistics.put(listPlayer, statistics);
        return characters;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getLetters/{vowelsRequired}/{consonantsRequired}")
    public String getLetters(Player player, @PathParam("vowelsRequired") int vowelsRequired, @PathParam("consonantsRequired") int consonantsRequired) {
        PlayerStatistics p = getPlayerStatisticsFromPlayer(player);
        Player listPlayer = findPlayer(player.getUsername());
        listPlayer.setPlayerStatus(PlayerStatus.PLAYING);
        String voweles = getRandomVowels(vowelsRequired);
        p.setVowels(voweles);
        String consonants = getRandomConsonants(consonantsRequired);
        p.setConsonants(consonants);
        return voweles + consonants;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{word}")
    public String addWord(Player player, @PathParam("word") String word) {
        try {
            PlayerStatistics p = getPlayerStatisticsFromPlayer(player);
            p.setWord(word);
            submitedWords++;
            String checkUpURL = DICTIONARY_LINK + word + "/json";
            URL url = new URL(checkUpURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader br = null;
            String output = null;
            try {
                br = new BufferedReader(new InputStreamReader(
                        (connection.getInputStream())));
                output = br.readLine();
                System.out.println(output);
            } catch (FileNotFoundException ex) {
                p.setWordStatus(WordStatus.INCORRECT);
                p.setScore(0);
                return INCORRECT_WORD;
            } catch (IOException ex) {
                Logger.getLogger(WordService.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(WordService.class.getName()).log(Level.SEVERE, null, ex);
                }
                Player listPlayer = findPlayer(player.getUsername());
                listPlayer.setPlayerStatus(PlayerStatus.ROUND_COMPLETED);
            }
            if(verifyWord(p)){
                p.setWordStatus(WordStatus.CORRECT);
                p.setScore(getPoints(p));
            }
            else{
                p.setWordStatus(WordStatus.ADALA_NA);
                return ResponseResult.ADALA_NA;
            }
            Player listedPlayer = findPlayer(player.getUsername());
            listedPlayer.setPlayerStatus(PlayerStatus.ROUND_COMPLETED);
            System.out.println(getPLAYER_ROUND_STATISTICS());
            return SUCCESS;
        } catch (MalformedURLException ex) {
            Logger.getLogger(WordService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WordService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ERROR;
    }
}
