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
import java.util.Set;

/**
 *
 * @author Lakshitha
 */
public class CurrentPlay {

    public static int currentRound = 0;
    
    private static final List<Player> PLAYERS = new ArrayList<Player>();

    private static final Map <Integer, Map<Player,PlayerStatistics>> PLAYER_ROUND_STATISTICS = new HashMap<Integer, Map<Player,PlayerStatistics>>();

    private static final Map<Integer, List<Map<Player, String>>> PLAYER_ROUND_WORDS = new HashMap<Integer, List<Map<Player, String>>>();

    public static List<Player> getPLAYERS() {
        return PLAYERS;
    }

    public static Map<Integer, Map<Player, PlayerStatistics>> getPLAYER_ROUND_STATISTICS() {
        return PLAYER_ROUND_STATISTICS;
    }

    public static Map<Integer, List<Map<Player, String>>> getPLAYER_ROUND_WORDS() {
        return PLAYER_ROUND_WORDS;
    }
    
    public static Player findUser(String username) {
        for (Player user : PLAYERS) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

}
