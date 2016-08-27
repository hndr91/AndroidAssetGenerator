/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package androidassetsgenerator;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author afifhendrawan
 */
public class MainGUI {

    JFrame mainFrame;
    JButton openBtn, outputBtn, startBtn;
    JTextField openURL, outputURL;
    JPanel openPanel, basePanel, outputPanel, sizePanel;
    JComboBox baseSizeCombo;
    JFileChooser openImg = new JFileChooser(), selectOutDir = new JFileChooser();
    public JCheckBox ldpi, mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi;
    public File selectedFile, selectedFolder;
    public String outputDir;

    public MainGUI() {
        initComponents();
    }

    private void initComponents() {
        mainFrame = new JFrame(Constants.TITLE);
        mainFrame.setSize(400, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        GridBagLayout mainLayout = new GridBagLayout();
        mainFrame.setLayout(mainLayout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        /**
         * Select Asset Panel
         */
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.125;
        openPanel = new JPanel();
        openPanel.setBorder(BorderFactory.createTitledBorder(Constants.OPEN_PANEL_TITLE));
        openPanel.setLayout(new GridBagLayout());
        GridBagConstraints openPanelCons = new GridBagConstraints();
        openPanelCons.fill = GridBagConstraints.HORIZONTAL;

        openPanelCons.gridx = 0;
        openPanelCons.gridy = 0;
        openPanelCons.weightx = 0.1;
        openBtn = new JButton();
        openBtn.setFont(new Font(Constants.LUCIA_GRANDE, Font.PLAIN, 14));
        openBtn.setText(Constants.OPEN_BUTTON);
        openBtn.addActionListener((ActionEvent e) -> {
            openImgAction(e);
        });
        openPanel.add(openBtn, openPanelCons);

        openPanelCons.gridx = 1;
        openPanelCons.gridy = 0;
        openPanelCons.weightx = 0.9;
        openURL = new JTextField();
        openURL.setEditable(false);
        openPanel.add(openURL, openPanelCons);

        mainFrame.add(openPanel, gbc);

        /**
         * Select Base Panel
         */
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.125;
        basePanel = new JPanel();
        basePanel.setBorder(BorderFactory.createTitledBorder(Constants.BASE_PANEL_TITLE));
        basePanel.setLayout(new GridBagLayout());
        GridBagConstraints basePanelCons = new GridBagConstraints();
        basePanelCons.fill = GridBagConstraints.HORIZONTAL;

        basePanelCons.gridx = 0;
        basePanelCons.gridy = 0;
        basePanelCons.weightx = 1.0;
        String[] baseSize = {
            Constants.LDPI,
            Constants.MDPI,
            Constants.HDPI,
            Constants.XHDPI,
            Constants.XXHDPI,
            Constants.XXXHDPI,};
        baseSizeCombo = new JComboBox(baseSize);
        baseSizeCombo.addItemListener((ItemEvent e) -> {
            baseSizeListener(e);
        });
        baseSizeCombo.setSelectedIndex(0);
        basePanel.add(baseSizeCombo, basePanelCons);

        mainFrame.add(basePanel, gbc);

        /**
         * Output Destination
         */
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.125;
        outputPanel = new JPanel();
        outputPanel.setBorder(BorderFactory.createTitledBorder(Constants.OUTPUT_PANEL_TITLE));
        outputPanel.setLayout(new GridBagLayout());
        GridBagConstraints outputPanelCons = new GridBagConstraints();
        outputPanelCons.fill = GridBagConstraints.HORIZONTAL;

        outputPanelCons.gridx = 0;
        outputPanelCons.gridy = 0;
        outputPanelCons.weightx = 0.1;
        outputBtn = new JButton();
        outputBtn.setFont(new Font(Constants.LUCIA_GRANDE, Font.PLAIN, 14));
        outputBtn.setText(Constants.OUTPUT_BUTTON);
        outputBtn.addActionListener((ActionEvent e) -> {
            outputImgAction(e);
        });
        outputPanel.add(outputBtn, outputPanelCons);

        outputPanelCons.gridx = 1;
        outputPanelCons.gridy = 0;
        outputPanelCons.weightx = 0.9;
        outputURL = new JTextField();
        outputURL.setEditable(false);
        outputPanel.add(outputURL, outputPanelCons);

        mainFrame.add(outputPanel, gbc);

        /**
         * Select Output
         */
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0.500;
        sizePanel = new JPanel();
        sizePanel.setLayout(new GridLayout(2, 3));
        sizePanel.setBorder(BorderFactory.createTitledBorder(Constants.SELECT_SIZE_TITLE));
        ldpi = new JCheckBox(Constants.LDPI);
        mdpi = new JCheckBox(Constants.MDPI);
        hdpi = new JCheckBox(Constants.HDPI);
        xhdpi = new JCheckBox(Constants.XHDPI);
        xxhdpi = new JCheckBox(Constants.XXHDPI);
        xxxhdpi = new JCheckBox(Constants.XXXHDPI);

        /** Set to false at the beginning */ 
        ldpi.setSelected(true);
        mdpi.setEnabled(false);
        hdpi.setEnabled(false);
        hdpi.setEnabled(false);
        xhdpi.setEnabled(false);
        xxhdpi.setEnabled(false);
        xxxhdpi.setEnabled(false);

        sizePanel.add(ldpi);
        sizePanel.add(mdpi);
        sizePanel.add(hdpi);
        sizePanel.add(xhdpi);
        sizePanel.add(xxhdpi);
        sizePanel.add(xxxhdpi);

        mainFrame.add(sizePanel, gbc);

        /**
         * Start Button
         */
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.5;
        gbc.weighty = 0.125;
        startBtn = new JButton();
        startBtn.setFont(new Font(Constants.LUCIA_GRANDE, Font.BOLD | Font.ITALIC, 14));
        startBtn.setText(Constants.GO);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateAction(e);
            }
        });
        mainFrame.add(startBtn, gbc);

        mainFrame.setVisible(true);
    }

    private void openImgAction(ActionEvent e) {
        int returnVal = openImg.showOpenDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFile = openImg.getSelectedFile();
            openURL.setText(selectedFile.getPath());
        }
    }

    private void baseSizeListener(ItemEvent e) {
        switch (baseSizeCombo.getSelectedIndex()) {
            case 0:
                ldpi.setEnabled(true);
                ldpi.setSelected(true);
                mdpi.setEnabled(false);
                hdpi.setEnabled(false);
                xhdpi.setEnabled(false);
                xxhdpi.setEnabled(false);
                xxxhdpi.setEnabled(false);
                break;
            case 1:
                ldpi.setEnabled(true);
                ldpi.setSelected(true);
                mdpi.setEnabled(true);
                mdpi.setSelected(true);
                hdpi.setEnabled(false);
                xhdpi.setEnabled(false);
                xxhdpi.setEnabled(false);
                xxxhdpi.setEnabled(false);
                break;
            case 2:
                ldpi.setEnabled(true);
                ldpi.setSelected(true);
                mdpi.setEnabled(true);
                mdpi.setSelected(true);
                hdpi.setEnabled(true);
                hdpi.setSelected(true);
                xhdpi.setEnabled(false);
                xxhdpi.setEnabled(false);
                xxxhdpi.setEnabled(false);
                break;
            case 3:
                ldpi.setEnabled(true);
                ldpi.setSelected(true);
                mdpi.setEnabled(true);
                mdpi.setSelected(true);
                hdpi.setEnabled(true);
                hdpi.setSelected(true);
                xhdpi.setEnabled(true);
                xhdpi.setSelected(true);
                xxhdpi.setEnabled(false);
                xxxhdpi.setEnabled(false);
                break;
            case 4:
                ldpi.setEnabled(true);
                ldpi.setSelected(true);
                mdpi.setEnabled(true);
                mdpi.setSelected(true);
                hdpi.setEnabled(true);
                hdpi.setSelected(true);
                xhdpi.setEnabled(true);
                xhdpi.setSelected(true);
                xxhdpi.setEnabled(true);
                xxhdpi.setSelected(true);
                xxxhdpi.setEnabled(false);
                break;
            case 5:
                ldpi.setEnabled(true);
                ldpi.setSelected(true);
                mdpi.setEnabled(true);
                mdpi.setSelected(true);
                hdpi.setEnabled(true);
                hdpi.setSelected(true);
                xhdpi.setEnabled(true);
                xhdpi.setSelected(true);
                xxhdpi.setEnabled(true);
                xxhdpi.setSelected(true);
                xxxhdpi.setEnabled(true);
                xxxhdpi.setSelected(true);
                break;
        }
    }

    private void outputImgAction(ActionEvent e) {
        selectOutDir.setCurrentDirectory(new File("."));
        selectOutDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selectOutDir.setAcceptAllFileFilterUsed(false);
        int returnVal = selectOutDir.showSaveDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFolder = selectOutDir.getSelectedFile();
            outputDir = selectedFolder.getAbsolutePath();
            outputDir = outputDir.replace("./", "");
            outputURL.setText(outputDir);
        }
    }

    private void generateAction(ActionEvent e) {
        AssetGenerator assetGenerator = new AssetGenerator();

        if (!openURL.getText().isEmpty() && !outputURL.getText().isEmpty()) {
            try {
                assetGenerator.scaleAsset(selectedFile, selectedFile.getName(), outputDir);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(mainFrame, Constants.ERROR_MESSAGE + ex, Constants.ERROR, JOptionPane.ERROR_MESSAGE);
            }
            
            JOptionPane.showMessageDialog(mainFrame, Constants.DONE_MESSAGE, Constants.INFO, JOptionPane.INFORMATION_MESSAGE);

        } else if (openURL == null || openURL.getText().isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, Constants.OPEN_URL_IS_EMPTY, Constants.WARNING, JOptionPane.WARNING_MESSAGE);
        } else if (outputURL == null || outputURL.getText().isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, Constants.OUTPUT_URL_IS_EMPTY, Constants.WARNING, JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainFrame, Constants.ERROR, Constants.WARNING, JOptionPane.WARNING_MESSAGE);
        }
        
    }

}
