package org.example;

import javax.swing.*;
import java.awt.*;

public class ChartPanel extends JPanel {
    private JLabel chartLabel;
    public ChartPanel(){
        this.setBounds(0, 90, 900, 620);
        this.setLayout(new FlowLayout());
        this.chartLabel= new JLabel();
        this.add(chartLabel);
    }

    public JLabel getChartLabel() {
        return chartLabel;
    }

    public void setChartLabel(JLabel chartLabel) {
        this.chartLabel = chartLabel;
    }
}
