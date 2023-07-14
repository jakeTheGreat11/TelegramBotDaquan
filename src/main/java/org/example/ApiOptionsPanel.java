package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


public class ApiOptionsPanel extends JPanel implements ActionListener {
    private JCheckBox apiQuotes= new JCheckBox("Quotes");
    private JCheckBox apiNumber= new JCheckBox("Number facts");
    private JCheckBox apiJoke= new JCheckBox("Jokes");
    private JCheckBox apiCovid19Data = new JCheckBox("Data covid-19");
    private JCheckBox apiCountry= new JCheckBox("Country");
    private JCheckBox[] checkBoxes;
    private HashMap<String, Integer> selectedCheckboxes = new HashMap<>(5);
    private JButton confirmButton = new JButton("confirm");
    private int selectedCount=0;


    public ApiOptionsPanel(){
        this.setLayout(null);
        this.setBounds(0,90,900,620);
        apiQuotes.addActionListener(this);
        apiNumber.addActionListener(this);
        apiJoke.addActionListener(this);
        apiCovid19Data.addActionListener(this);
        apiCountry.addActionListener(this);
        confirmButton.addActionListener(this);
        apiQuotes.setBounds(10, 10, 150, 30);
        apiNumber.setBounds(160, 10, 150, 30);
        apiJoke.setBounds(310, 10, 150, 30);
        apiCovid19Data.setBounds(10, 40, 150, 30);
        apiCountry.setBounds(160, 40, 150, 30);
        confirmButton.setBounds(160,150,150,50);
        this.add(confirmButton);
        this.add(apiQuotes);
        this.add(apiNumber);
        this.add(apiJoke);
        this.add(apiCovid19Data);
        this.add(apiCountry);
        selectedCheckboxes.put(apiCountry.getText(),0);
        selectedCheckboxes.put(apiCovid19Data.getText(),0);
        selectedCheckboxes.put(apiQuotes.getText(),0);
        selectedCheckboxes.put(apiNumber.getText(),0);
        selectedCheckboxes.put(apiJoke.getText(),0);
        checkBoxes = new JCheckBox[]{apiQuotes, apiNumber, apiJoke, apiCovid19Data, apiCountry};
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectedCount = 0;
        for (JCheckBox checkbox : checkBoxes) {
            if (checkbox.isSelected()) {
                selectedCount++;
                selectedCheckboxes.put(checkbox.getText(), 1);
            } else {
                selectedCheckboxes.put(checkbox.getText(), 0);
            }
        }

        if (e.getSource() == confirmButton) {
            if (selectedCount != 3) {
                JOptionPane.showMessageDialog(null, "sorry choose only 3 api's");
                System.out.println(selectedCheckboxes);
            }
        }
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JCheckBox[] getCheckBoxes() {
        return checkBoxes;
    }

    public int getSelectedCount() {
        return selectedCount;
    }

    public HashMap<String, Integer> getSelectedCheckboxes() {
        return selectedCheckboxes;
    }
}
