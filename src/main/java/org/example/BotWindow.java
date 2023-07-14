package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BotWindow extends JFrame implements ActionListener{
    private ApiOptionsPanel apiOptions;
    private JButton apiSelectButton;

    private InteractionHistoryPanel interactionHistoryPanel;
    private JButton interactionHistoryButton;


    private UserStatisticsPanel userStatisticsPanel;
    private JButton userStatisticsButton;

    private ChartPanel chartPanel;
    private JButton chartButton;

    private boolean show = true;

    public BotWindow() {
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setBounds(300, 80, 900, 700);
            this.setResizable(false);
            this.setLayout(null);
            this.apiOptions = new ApiOptionsPanel();
            this.add(apiOptions);
            this.apiSelectButton = new JButton("Activities management");
            this.apiSelectButton.setBounds(10, 10, 180, 80);
            this.apiSelectButton.setVisible(true);
            this.add(apiSelectButton);
            this.apiSelectButton.addActionListener(this);
            this.apiOptions.getConfirmButton().addActionListener(this);
            this.apiOptions.setVisible(false);

            this.interactionHistoryPanel = new InteractionHistoryPanel();
            this.interactionHistoryPanel.setVisible(false);
            this.interactionHistoryButton= new JButton("Interaction History");
            this.interactionHistoryButton.setBounds(210,10,180, 80);
            this.add(interactionHistoryPanel);
            this.add(interactionHistoryButton);
            this.interactionHistoryButton.addActionListener(this);


            this.userStatisticsPanel= new UserStatisticsPanel();
            this.userStatisticsPanel.setVisible(false);
            this.userStatisticsButton= new JButton("User Statistics");
            this.userStatisticsButton.setBounds(400,10,180, 80);
            this.add(userStatisticsPanel);
            this.add(userStatisticsButton);
            this.userStatisticsButton.addActionListener(this);


            this.chartPanel = new ChartPanel();
            this.chartPanel.setVisible(false);
            this.chartButton = new JButton("api Chart");
            this.chartButton.setBounds(600,10,180,80);
            this.add(chartPanel);
            this.add(chartButton);
            this.chartButton.addActionListener(this);

            this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == apiSelectButton) {
            this.apiOptions.setVisible(show);
            this.interactionHistoryPanel.setVisible(!show);
            this.userStatisticsPanel.setVisible(!show);
            this.chartPanel.setVisible(!show);
        }
        if (e.getSource() == interactionHistoryButton){
            this.interactionHistoryPanel.setVisible(show);
            this.apiOptions.setVisible(!show);
            this.userStatisticsPanel.setVisible(!show);
            this.chartPanel.setVisible(!show);
        }
        if (e.getSource()== userStatisticsButton){
            this.userStatisticsPanel.setVisible(show);
            this.apiOptions.setVisible(!show);
            this.interactionHistoryPanel.setVisible(!show);
            this.chartPanel.setVisible(!show);

        }
        if (e.getSource()== chartButton){
            this.chartPanel.setVisible(show);
            this.interactionHistoryPanel.setVisible(!show);
            this.apiOptions.setVisible(!show);
            this.userStatisticsPanel.setVisible(!show);

        }
    }

    public ApiOptionsPanel getApiOptions() {
        return apiOptions;
    }

    public InteractionHistoryPanel getInteractionHistoryPanel() {
        return interactionHistoryPanel;
    }

    public UserStatisticsPanel getUserStatisticsPanel() {
        return userStatisticsPanel;
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}
