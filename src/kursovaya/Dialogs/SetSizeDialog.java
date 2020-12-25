package kursovaya.Dialogs;

import kursovaya.game_logic.Universe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SetSizeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField rowsInput;
    private JTextField colsInput;


    public SetSizeDialog(Universe universe, GridBagConstraints constraints) {
        rowsInput.setText(String.valueOf(universe.getHeight()));
        colsInput.setText(String.valueOf(universe.getWidth()));
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(universe);
            }
        });

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
        pack();
    }

    private void onOK(Universe universe) {
        String RowsString = rowsInput.getText();
        String ColsString = colsInput.getText();
        int Rows;
        int Cols;
        if (RowsString.isEmpty() || ColsString.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all the text fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Rows = Integer.parseInt(RowsString);
            Cols = Integer.parseInt(ColsString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Incorrect number format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ((Rows <= 0) || (Cols <= 0)) {
            JOptionPane.showMessageDialog(this,
                    "Please enter positive numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ((Rows > 100) || (Cols > 100)) {
            JOptionPane.showMessageDialog(this,
                    "Maximum length/width is 100", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        universe.reSize(Rows, Cols);
        dispose();

    }

    private void onCancel() {
        dispose();
    }
}