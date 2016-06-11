/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.common;

import com.nsbm.entity.Player;
import java.util.Arrays;
import javax.swing.DefaultListModel;

/**
 *
 * @author Lakshitha
 */
public class CommonUtil {

    public static void setModelData(Player[] players, DefaultListModel<String> model) {
        for (Player p : players) {
            System.out.println(p.getUsername());
            if (p.getUsername().equals(CommonData.username)) {
                model.addElement("You joined");
            } else {
                model.addElement(p.getUsername() + " joined");
            }
        }
    }

    public static void setModelData(String player, DefaultListModel<String> model) {
        model.add(0, player + " joined");
    }
    
    public static void setRoundCompletedModelData(String[] statistics, DefaultListModel<String> model) {
        for (String p : statistics) {
            p = p.replaceAll("@", " ");
            model.addElement(p);
        }
    }

    public static void setRoundCompletedModelData(String statistic, DefaultListModel<String> model) {
        statistic = statistic.replaceAll("@", " ");
        model.add(0, statistic);
    }
}
