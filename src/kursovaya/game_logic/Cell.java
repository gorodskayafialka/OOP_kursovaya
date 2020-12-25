package kursovaya.game_logic;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cell extends JPanel {
    private boolean Alive;
    private Color DeadColor;
    private Color LiveColor;

    public Cell() {
        Alive = false;
        DeadColor = Color.WHITE;
        LiveColor = Color.BLACK;
        setBackground(DeadColor);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (!isAlive()) {
                    Alive = true;
                    setBackground(LiveColor);
                } else {
                    Alive = false;
                    setBackground(DeadColor);
                }
            }
        });

    }

    public boolean isAlive() {
        return Alive;
    }

    public void setAlive(boolean alive) {
        Alive = alive;
        if (alive)
            setBackground(LiveColor);
        else
            setBackground(DeadColor);
    }

}

