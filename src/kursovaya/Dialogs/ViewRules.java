package kursovaya.Dialogs;

import javax.swing.*;
import java.awt.event.*;

public class ViewRules extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JLabel RulesText;

    public ViewRules() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);
        setSize(550, 250);


        RulesText.setText("<html>The rules of the game are as follows:" +
                "<br>1. Any live cell with two or three live neighbours survives." +
                "<br>2. Any dead cell with three live neighbours becomes a live cell." +
                "<br>3. All other live cells die in the next generation. Similarly, all other dead cells stay dead." +
                "<br><br>" +
                "Use your mouse to create the first generation of cells." +
                "<br>You can choose the size of the field as well as the game mode using the menu." +
                "<<<Minimum size is 1x1,maximum size is 100x100/html>");


        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        ViewRules dialog = new ViewRules();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
