package kursovaya.game_logic;

import java.util.ArrayList;

public class Universe {
    private State previousState;
    private State currentState;
    private int Uneditable;
    private ArrayList<ArrayList<Cell>> universe;

    public Universe(int width, int height) {
        universe = new ArrayList<>();
        Uneditable = 1;
        currentState = null;
        for (int i = 0; i < width; i++) {
            universe.add(i, new ArrayList<>());
            for (int j = 0; j < height; j++) {
                universe.get(i).add(j, new Cell());
            }
        }
    }

    public void nextStep() {
        if ((currentState == null) || (currentState.countLivingCells() == 0) || (Uneditable < 2)) {
            previousState = new State(universe.size(), universe.get(0).size());
            for (int i = 0; i < universe.size(); i++) {
                for (int j = 0; j < universe.get(0).size(); j++) {
                    previousState.field[i][j] = universe.get(i).get(j).isAlive();
                }
            }
        } else {
            previousState = currentState;
        }
        if (Uneditable == 1) Uneditable = 2;
        currentState = new State(universe.size(), universe.get(0).size());
        for (int i = 0; i < universe.size(); i++) {
            for (int j = 0; j < universe.get(0).size(); j++) {
                switch (previousState.countNeighbours(i, j)) {
                    case 2:
                        if (previousState.field[i][j])
                            currentState.field[i][j] = true;
                        else
                            currentState.field[i][j] = false;
                        if (universe.get(i).get(j).isAlive() != currentState.field[i][j])
                            universe.get(i).get(j).setAlive(currentState.field[i][j]);
                        break;
                    case 3:
                        universe.get(i).get(j).setAlive(true);
                        currentState.field[i][j] = true;
                        break;
                    default:
                        universe.get(i).get(j).setAlive(false);
                        currentState.field[i][j] = false;
                        break;
                }
            }
        }
    }

    public int getLivingCells() {
        return currentState.countLivingCells();
    }

    public void Clear() {
        for (int i = 0; i < universe.size(); i++) {
            for (int j = 0; j < universe.get(0).size(); j++) {
                if (universe.get(i).get(j).isAlive())
                    universe.get(i).get(j).setAlive(false);
            }
        }
    }

    public void setUnditable(int uneditable) {
        Uneditable = uneditable;
    }

    public boolean sameState() {
        if (currentState.equals(previousState)) return true;
        else return false;
    }

    public void reSize(int rows, int cols) {
        int oldCols = getWidth();
        int oldRows = getHeight();
        int newRows;
        if (rows > oldRows) {
            newRows = oldRows;
            for (int i = oldRows; i < rows; i++) {
                universe.add(i, new ArrayList<>());
                for (int j = 0; j < cols; j++) {
                    universe.get(i).add(new Cell());
                }
            }
        } else {
            newRows = rows;
            for (int i = (oldRows - 1); i >= rows; i--) {
                universe.remove(i);
            }
        }
        if (cols == oldCols) return;
        if (cols > oldCols) {
            for (int i = 0; i < newRows; i++) {
                for (int j = oldCols; j < cols; j++) {
                    universe.get(i).add(new Cell());
                }
            }
        } else {
            for (int i = 0; i < newRows; i++) {
                for (int j = (oldCols - 1); j >= cols; j--) {
                    universe.get(i).remove(j);
                }
            }
        }
    }

    public int getWidth() {
        return universe.get(0).size();
    }

    public int getHeight() {
        return universe.size();
    }

    public Cell getElement(int x, int y)
    {
        return universe.get(x).get(y);
    }
}