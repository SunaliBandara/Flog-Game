/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.service;

import com.nsbm.common.CurrentPlay;
import com.nsbm.common.PlayerStatus;
import static com.nsbm.common.ResponseResult.EXISTINGPLAYER;
import static com.nsbm.common.ResponseResult.SUCCESS;
import com.nsbm.entity.Player;
import java.util.List;
import java.util.Set;
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
    public String addPlayer(Player user) {
        user.setPlayerStatus(PlayerStatus.JOINED);
        Set<Player> players = CurrentPlay.getPLAYERS(); 
        boolean isAdded = players.add(user);
        
        if (!isAdded) {
            return EXISTINGPLAYER;
        }      
        System.out.println(user.getUsername() + " Joined");
        return SUCCESS;
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPlayers")
    public Set<Player> getPlayers(){
        return CurrentPlay.getPLAYERS();
    }
}
