/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.common;

import com.nsbm.entity.PlayerStatistics;
import com.nsbm.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lakshitha
 */
public class CurrentPlay {

    public static int currentRound = 0;
    public static int submitedWords = 0;
    
    private static final List<Player> PLAYERS = new ArrayList<Player>();

    private static final Map <Integer, Map<Player,PlayerStatistics>> PLAYER_ROUND_STATISTICS = new HashMap<Integer, Map<Player,PlayerStatistics>>();

    private static final Map<Player, Integer> SPECIAL_POINS = new HashMap<Player, Integer>();

    public static List<Player> getPLAYERS() {
        return PLAYERS;
    }

    public static Map<Integer, Map<Player, PlayerStatistics>> getPLAYER_ROUND_STATISTICS() {
        return PLAYER_ROUND_STATISTICS;
    }

    public static Map<Player,Integer> getSPECIAL_POINTS(){
        return SPECIAL_POINS;
    }
}
