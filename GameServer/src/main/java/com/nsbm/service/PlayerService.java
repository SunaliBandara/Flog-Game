/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.service;

import com.google.gson.Gson;
import static com.nsbm.common.CommonUtil.findPlayer;
import static com.nsbm.common.CommonUtil.findRoundCompletedPlayers;
import static com.nsbm.common.CommonUtil.getPlayerStatisticsFromPlayer;
import com.nsbm.common.CurrentPlay;
import com.nsbm.common.PlayerStatus;
import static com.nsbm.common.ResponseResult.EXISTINGPLAYER;
import static com.nsbm.common.ResponseResult.SUCCESS;
import com.nsbm.entity.Player;
import com.nsbm.entity.PlayerStatistics;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Lakshitha
 */
@Path("/PlayerService")
public class PlayerService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addPlayer")
    public String addPlayer(Player player) {
        List<Player> players = CurrentPlay.getPLAYERS(); 
        boolean isAdded = players.contains(player);
        
        if (isAdded) {
            return EXISTINGPLAYER;
        }      
        players.add(0, player);
        System.out.println(player.getUsername() + " Joined");
        Player listPlayer = findPlayer(player.getUsername());
        listPlayer.setPlayerStatus(PlayerStatus.JOINED);
        return SUCCESS;
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPlayers")
    public List<Player> getPlayers(){
        return CurrentPlay.getPLAYERS();
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getRoundCompletedPlayers")
    public String getRoundCompletedPlayers(){
        List<String> completedPlayer = new ArrayList<String>();
        List<Player> players = findRoundCompletedPlayers();
        for(Player player : players){
            PlayerStatistics statistics = getPlayerStatisticsFromPlayer(player);
            String statisticString = player.getUsername() + "@" + statistics.getWordStatus() 
                    + "@" + statistics.getScore() + "@" + statistics.getWord();
            completedPlayer.add(statisticString);
        }
        return new Gson().toJson(completedPlayer);
    }
}
