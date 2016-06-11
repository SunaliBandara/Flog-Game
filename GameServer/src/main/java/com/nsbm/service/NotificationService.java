/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.service;

import static com.nsbm.common.CommonUtil.getPlayerStatisticsFromPlayer;
import static com.nsbm.common.CurrentPlay.currentRound;
import com.nsbm.entity.Player;
import com.nsbm.entity.PlayerStatistics;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

/**
 *
 * @author Lakshitha
 */
@Singleton
@Path("/BroadCaster")
public class NotificationService {
    private SseBroadcaster broadcaster = new SseBroadcaster();
 
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/sendPlayerJoin")
    public String broadcastJoinMessage(String message) {
        OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
        OutboundEvent event = eventBuilder.name("joined")
            .mediaType(MediaType.TEXT_PLAIN_TYPE)
            .data(String.class, message)
            .build();
 
        broadcaster.broadcast(event);
 
        return "Your have Joined";
    }
 
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    @Path("/listenPlayerJoin")
    public EventOutput listenToJoinBroadcast() {
        final EventOutput eventOutput = new EventOutput();
        this.broadcaster.add(eventOutput);
        return eventOutput;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/sendPlayerRoundCompletion")
    public String broadCastRoundCompletionMessage(Player player) {
        PlayerStatistics statistics = getPlayerStatisticsFromPlayer(player);
        OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
        OutboundEvent event = eventBuilder.name("completed")
            .mediaType(MediaType.TEXT_PLAIN_TYPE)
            .data(String.class, player.getUsername()+"@"+statistics.getWordStatus()+"@"+statistics.getWord())
            .build();
        broadcaster.broadcast(event);
 
        return "Your have completed the round " + currentRound;
    }
    
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    @Path("/listenRoundCompletion")
    public EventOutput listenToRoundCompletionBroadcast() {
        final EventOutput eventOutput = new EventOutput();
        this.broadcaster.add(eventOutput);
        return eventOutput;
    }
}
