package com.squid;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPanel extends JPanel {
    private JRadioButton std;
    private JRadioButton custom;
    private ButtonGroup bg;

    private JSlider slider;
    private JButton choose;
    private GridBagConstraints gbc;
    private JCheckBox cb;
    private JCheckBox inv;
    private JFileChooser fc;

    private Filter filter;
    private ImagePanel ip;
    private Window window;

    public SettingPanel() {
        setPreferredSize(new Dimension(250, 250));

        setButtons();
        setGroundLayout();
        setVisible(true);

        createNew("src/data/test3.jpg");

        addBList();
    }

    // Sets the layout
    private void setGroundLayout() {
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(choose, gbc);

        gbc.gridy = 1;
        add(std, gbc);

        gbc.gridy = 2;
        add(custom, gbc);

        gbc.gridy = 3;
        add(slider, gbc);

        gbc.gridy = 4;
        add(inv, gbc);

        gbc.gridy = 5;
        add(cb, gbc);
    }

    // Sets all the buttons
    private void setButtons() {
        std = new JRadioButton("Standard deviation", true);
        custom = new JRadioButton("Custom deviation");

        bg = new ButtonGroup();
        bg.add(std);
        bg.add(custom);

        slider = new JSlider(0, 255, 0);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(25);

        cb = new JCheckBox("Black and white", false);
        inv = new JCheckBox("Inverted deviation", false);
        choose = new JButton("Select a file");
    }

    private void addBList() {
        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fc = new JFileChooser(FileSystemView.getFileSystemView());
                fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
                fc.setAcceptAllFileFilterUsed(false);

                int val = fc.showOpenDialog(null);
                if (val == JFileChooser.APPROVE_OPTION) {
                    filter = new Filter(fc.getSelectedFile().getPath());
                    ip = new ImagePanel(filter);
                    window = new Window(ip);
                }
            }
        });

        std.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (std.isSelected()) {
                    if (inv.isSelected()) filter.renew(true, true);
                    else filter.renew(false, true);
                } else {
                    filter.setDeviation(slider.getValue());

                    if (inv.isSelected()) filter.renew(true, false);
                    else filter.renew(false, false);
                }

                ip.repaint();
            }
        });

        // Very sketchy, because the entire image is being updated every move
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (custom.isSelected()) {
                    filter.setDeviation(slider.getValue());

                    if (inv.isSelected()) filter.renew(true, false);
                    else filter.renew(false, false);

                    ip.repaint();
                }
            }
        });

        // Checkbox for the gray filter
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cb.isSelected()) filter.setGray(true);
                else filter.setGray(false);
                
                ip.repaint();
            }
        });

        // Checks if the deviation of the image is inverted or not
        inv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inv.isSelected()) {
                    if (std.isSelected()) filter.renew(true, true);
                    else filter.renew(true, false);
                } else {
                    if (std.isSelected()) filter.renew(false, true);
                    else filter.renew(false, false);
                }

                ip.repaint();
            }
        });
    }

    private void createNew(String path) {
        filter = new Filter(path);
        ip = new ImagePanel(filter);
        window = new Window(ip);

        ip.repaint();
    }
}