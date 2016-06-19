/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.entity;

import com.nsbm.common.WordStatus;

/**
 *
 * @author Lakshitha
 */
public class PlayerStatistics {
    private String initialLetters;
    private String word;
    private String consonants;
    private String vowels;
    private WordStatus wordStatus;
    private Integer score;
    
    public String getInitialLetters() {
        return initialLetters;
    }

    public void setInitialLetters(String initialLetters) {
        this.initialLetters = initialLetters;
    }

    public String getWord() {
        return word;
    }

    public String getConsonants() {
        return consonants;
    }

    public void setConsonants(String consonants) {
        this.consonants = consonants;
    }

    public String getVowels() {
        return vowels;
    }

    public void setVowels(String vowels) {
        this.vowels = vowels;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public WordStatus getWordStatus() {
        return wordStatus;
    }

    public void setWordStatus(WordStatus wordStatus) {
        this.wordStatus = wordStatus;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "PlayerStatistics{" + "initialLetters=" + initialLetters + ", word=" + word + ", consonants=" + consonants + ", vowels=" + vowels + ", wordStatus=" + wordStatus + ", score=" + score + '}';
    }

}
