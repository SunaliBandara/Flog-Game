/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.service;

import static com.nsbm.common.CurrentPlay.currentRound;
import static com.nsbm.common.CurrentPlay.getPLAYER_ROUND_STATISTICS;
import static com.nsbm.common.ResponseResult.SUCCESS;
import com.nsbm.entity.PlayerStatistics;
import com.nsbm.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
    @Context HttpSession httpSession;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getInitialLetters")
    public String getInitialLetters(Player player){

        Random r = new Random();
        String characters = new String();

        for(int i=0; i < INITIAL_LIMIT; i++){
            char c = (char) (r.nextInt(26) + 'a');
            characters = characters.concat(String.valueOf(c));
        }
        
        PlayerStatistics statistics = new PlayerStatistics();
        statistics.setInitialLetters(characters);
        
        Map<Integer, Map<Player,PlayerStatistics>> playerRoundStatistics = getPLAYER_ROUND_STATISTICS();
        Map<Player, PlayerStatistics> playerStatistics = playerRoundStatistics.get(currentRound);
        if(playerStatistics==null){
            playerStatistics = new HashMap<Player, PlayerStatistics>();
            playerRoundStatistics.put(currentRound, playerStatistics);
        }
        //playerLetters and playerLetter
        playerStatistics.put(player, statistics);
        return characters;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getLetters/{requiredNumber}")
    public String getLetters(Player player, @PathParam("requiredNumber")int requiredNumber){
        Random r = new Random();
        String characters = new String();

        for(int i=0; i < requiredNumber; i++){
            char c = (char) (r.nextInt(26) + 'a');
            characters = characters.concat(String.valueOf(c));
        }

        Map<Integer, Map<Player,PlayerStatistics>> playerRoundStatistics = getPLAYER_ROUND_STATISTICS();
        Map<Player, PlayerStatistics> playerStatistics = playerRoundStatistics.get(currentRound);
        
        PlayerStatistics p = playerStatistics.get(player);
        p.setWord(characters);
        System.out.println(playerRoundStatistics);
        return characters;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{word}")
    public String addWord(Player user, @PathParam("word") String word) {
        return SUCCESS;
    }
}
