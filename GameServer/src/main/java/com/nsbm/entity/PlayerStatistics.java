/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.entity;

/**
 *
 * @author Lakshitha
 */
public class PlayerStatistics {
    private int noOfPickedLetters;
    private String initialLetters;
    private String pickedLetters;
    private String word;

    public int getNoOfPickedLetters() {
        return noOfPickedLetters;
    }

    public void setNoOfPickedLetters(int noOfPickedLetters) {
        this.noOfPickedLetters = noOfPickedLetters;
    }

    public String getInitialLetters() {
        return initialLetters;
    }

    public void setInitialLetters(String initialLetters) {
        this.initialLetters = initialLetters;
    }

    public String getPickedLetters() {
        return pickedLetters;
    }

    public void setPickedLetters(String pickedLetters) {
        this.pickedLetters = pickedLetters;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "PlayerStatistics{" + "noOfPickedLetters=" + noOfPickedLetters + ", initialLetters=" + initialLetters + ", pickedLetters=" + pickedLetters + ", word=" + word + '}';
    }

}
