package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InteractionHistoryPanel extends JPanel {
    private String[] historyListModel;
    private JLabel[] historyLabel;
    private int y;
    private int j;
    public InteractionHistoryPanel () {
        this.setBounds(0, 90, 900, 620);
        this.setLayout(null);
        this.y = 20;
        this.j= 0;
        historyListModel = new String[10];
        this.historyLabel= new JLabel[10];
        for (int i = 0; i < historyLabel.length; i++) {
            JLabel jLabel = new JLabel();
            historyLabel[i]=jLabel;
            this.add(historyLabel[i]);
            this.historyLabel[i].setBounds(200,y,500,30);
            y+=50;
        }
    }
    // Method to update the interaction history
    public void updateHistory(String[] interaction) {
        if (j ==10){
            j=0;
        }
        historyLabel[j].setText((interaction[0]+" "+interaction[1]+" "+interaction[2]));
        j++;
        }
    }

