package org.example;

import javax.swing.*;

public class UserStatisticsPanel extends JPanel {
    private JLabel mostPopularApi;
    private String mostPopularApiText;

    private JLabel ApiRequestCount;
    private String ApiRequestCountText;


    private JLabel UniqueUsers;
    private String uniqueUsersText;

    private JLabel MostActiveUser;
    private String mostActiveUserText;

    public UserStatisticsPanel(){
        this.setBounds(0, 90, 900, 620);
        this.setLayout(null);
        this.ApiRequestCount = new JLabel();
        this.ApiRequestCount.setBounds(50,40,400,20);

        this.UniqueUsers = new JLabel();
        this.UniqueUsers.setBounds(50,100,400,20);

        this.MostActiveUser= new JLabel();
        this.MostActiveUser.setBounds(50,160,400,20);

        this.mostPopularApi = new JLabel();
        this.mostPopularApi.setBounds(50,220,400,20);

        this.add(ApiRequestCount);
        this.add(UniqueUsers);
        this.add(MostActiveUser);
        this.add(mostPopularApi);
    }
    public void setUniqueUsersText(String uniqueUser) {
        UniqueUsers.setText("number of unique users: "+uniqueUser);
    }
    public void setMostActiveUserText(String mostActiveUserText) {
        this.MostActiveUser.setText(mostActiveUserText);
    }

    public void setMostPopularApiText(String mostPopularApiText) {
        this.mostPopularApi.setText("most popular api: " + mostPopularApiText);
    }

    public void setApiRequestCountText(String apiRequestCountText) {
        this.ApiRequestCount.setText("total api requests: "+apiRequestCountText);
    }
}
