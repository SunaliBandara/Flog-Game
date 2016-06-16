/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.common;

import static com.nsbm.common.CommonData.VOWELS;
import static com.nsbm.common.CurrentPlay.currentRound;
import static com.nsbm.common.CurrentPlay.getPLAYERS;
import static com.nsbm.common.CurrentPlay.getPLAYER_ROUND_STATISTICS;
import static com.nsbm.common.CurrentPlay.getSPECIAL_POINTS;
import com.nsbm.entity.Player;
import com.nsbm.entity.PlayerStatistics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Lakshitha
 */
public class CommonUtil {

    public static PlayerStatistics getPlayerStatisticsFromPlayer(Player player) {
        Map<Integer, Map<Player, PlayerStatistics>> playerRoundStatistics = getPLAYER_ROUND_STATISTICS();
        Map<Player, PlayerStatistics> playerStatistics = playerRoundStatistics.get(currentRound);
        return playerStatistics.get(player);
    }

    public static String getRandomVowels(int vowelsRequired) {
        Random r = new Random();
        String vowels = new String();
        for (int i = 0; i < vowelsRequired; i++) {
            int c = r.nextInt(VOWELS.length);
            vowels = vowels.concat(String.valueOf(VOWELS[c]));
        }
        return vowels;
    }

    public static String getRandomConsonants(int consonantsRequired) {
        Random r = new Random();
        String consonants = new String();
        for (int i = 0; i < consonantsRequired;) {
            boolean vowelFound = false;
            char c = (char) (r.nextInt(26) + 'a');
            for (char vowel : VOWELS) {
                if (c == vowel) {
                    vowelFound = true;
                }
            }
            if (vowelFound) {
                continue;
            }
            i++;
            consonants = consonants.concat(String.valueOf(c));
        }
        return consonants;
    }
    
    public static Player findPlayer(String username) {
        for (Player player : getPLAYERS()) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }
    
    public static List<Player> findRoundCompletedPlayers() {
        List<Player> roundCompletedPlayers = new ArrayList<Player>();
        for (Player player : getPLAYERS()) {
            if (player.getPlayerStatus() == PlayerStatus.ROUND_COMPLETED) {
                roundCompletedPlayers.add(player);
            }
        }
        return roundCompletedPlayers;
    }
    
    public static boolean checkRoundEnd(){
        boolean roundEnd = true;
        for(Player player : getPLAYERS()){
            if(player.getPlayerStatus()!=PlayerStatus.ROUND_COMPLETED){
                roundEnd = false;
                break;
            }
        }
        return roundEnd;
    }
    
    public static boolean verifyWord(PlayerStatistics statistics){
        String word = statistics.getWord();
        boolean validWord = true;
        StringBuilder buffer = new StringBuilder();
        buffer.append(statistics.getInitialLetters());
        buffer.append(statistics.getConsonants());
        buffer.append(statistics.getVowels());

        char [] result = buffer.toString().toCharArray();
        for (int i = 0; i < word.length(); i++) {
            char wordLetter = word.charAt(i);
            if(!validWord){
               return false; 
            }
            validWord = false;
            for(char c : result){
                if(wordLetter == c){
                    validWord = true;
                }
            }
        }
        return true;
    }
    
    public static int getPoints(PlayerStatistics statistics){
        int wordValue = 0;
        for(char c : statistics.getInitialLetters().toCharArray()){
            if(statistics.getWord().indexOf(c) > 0){
                wordValue = wordValue + 5;
            }
        }
        wordValue = wordValue + statistics.getWord().length();
        return wordValue;
    }
    
    public static int getCurrentSpecialPoints(Player player){
        Map<Player,Integer> specialPoints = getSPECIAL_POINTS();
        return specialPoints.get(player);
    }
}
