package kursovaya;

import kursovaya.Dialogs.SetSizeDialog;
import kursovaya.Dialogs.ViewRules;
import kursovaya.game_logic.Universe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private JButton rulesButton;
    private JButton setSize;
    private JPanel cells;
    private JPanel mainPanel;
    private JPanel BtnsPanel;
    private Timer timer;
    private Universe universe;
    private JButton startButton;
    private JButton stopButton;
    private JButton stepButton;
    private JButton clearButton;
    private GridBagConstraints constraints;
    private int defaultWidth;
    private int defaultHeight;

    public MainFrame() {
        init();
        setUp();
        setTitle("Game of Life");
        setSize(defaultWidth, defaultHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        rulesButton.addActionListener(e -> {
            JDialog rulesView = new ViewRules();
            rulesView.setModal(true);
            rulesView.setLocationRelativeTo(this);
            rulesView.setVisible(true);
        });

        setSize.addActionListener(e -> {
            JDialog setSizeDialog = new SetSizeDialog(universe, constraints);
            setSizeDialog.setModal(true);
            setSizeDialog.setLocationRelativeTo(this);
            setSizeDialog.setVisible(true);
            cells.removeAll();
            paintCells();
            cells.repaint();
            cells.revalidate();
        });

        startButton.addActionListener(e -> {
            timer.start();
            startButton.setEnabled(false);
            stepButton.setEnabled(false);
            stopButton.setEnabled(true);
            setSize.setEnabled(false);
            clearButton.setEnabled(false);
            universe.setUnditable(1);

        });

        stopButton.addActionListener(e -> {
                    timer.stop();
                    startButton.setEnabled(true);
                    stepButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    setSize.setEnabled(true);
                    clearButton.setEnabled(true);
                    universe.setUnditable(0);
                }
        );

        stepButton.addActionListener(e -> {
            universe.nextStep();
        });


        clearButton.addActionListener(e -> {
            universe.Clear();
        });

    }

    void init() {
        mainPanel = new JPanel(new BorderLayout());
        cells = new JPanel(new GridBagLayout());
        BtnsPanel = new JPanel(new GridLayout(0, 2, 0, 5));
        startButton = new JButton("Start");
        setSize = new JButton("Set size");
        stopButton = new JButton("Stop");
        stepButton = new JButton("Next step");
        rulesButton = new JButton("View rules");
        clearButton = new JButton("Clear the screen");
        universe = new Universe(20, 20);
        constraints = new GridBagConstraints();
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                universe.nextStep();
                AutoStop();
            }

        });
    }

    void setUp() {
        BtnsPanel.add(startButton);
        BtnsPanel.add(stopButton);
        BtnsPanel.add(stepButton);
        BtnsPanel.add(clearButton);
        BtnsPanel.add(setSize);
        BtnsPanel.add(rulesButton);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        stopButton.setEnabled(false);
        mainPanel.add(cells);
        setContentPane(mainPanel);
        mainPanel.add(BtnsPanel, BorderLayout.AFTER_LAST_LINE);
        defaultWidth = 600;
        defaultHeight = 700;
        paintCells();
    }

    void AutoStop() {
        if (universe.getLivingCells() == 0) {
            stop();
            JOptionPane.showMessageDialog(new JDialog(),
                    "All cells are dead.", "AutoStop", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        if (universe.sameState()) {
            stop();
            JOptionPane.showMessageDialog(new JDialog(),
                    "The universe has entered a permanent state.", "AutoStop", JOptionPane.PLAIN_MESSAGE);
        }
    }

    void stop() {
        timer.stop();
        startButton.setEnabled(true);
        stepButton.setEnabled(true);
        stopButton.setEnabled(false);
        setSize.setEnabled(true);
        clearButton.setEnabled(true);
        universe.setUnditable(0);
    }

    void paintCells() {
        int height = universe.getHeight();
        int width = universe.getWidth();
        constraints.anchor = GridBagConstraints.CENTER;
        if (height < width) {
            setSize(defaultWidth, defaultWidth * height / width + BtnsPanel.getHeight());
        } else {
            if (height > width) {
                setSize(defaultHeight * width / height, defaultHeight);
            } else setSize(defaultWidth, defaultHeight);
        }
        for (int i = 0; i < universe.getHeight(); i++) {
            constraints.gridy = i;
            for (int j = 0; j < universe.getWidth(); j++) {
                constraints.gridx = j;
                cells.add(universe.getElement(i, j), constraints);
            }
        }
    }
}