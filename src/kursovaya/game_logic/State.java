package kursovaya.game_logic;

import java.util.Arrays;

public class State {
    public boolean[][] field;

    public State(int rows, int cols) {
        field = new boolean[rows][cols];
    }

    public int countLivingCells() {
        int counter = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j]) counter++;
            }
        }
        return counter;
    }

    public int countNeighbours(int x, int y) {
        int counter = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (((i != 0) || (j != 0)) && (x + i >= 0) && (y + j >= 0)
                        && (x + i < field.length) && (y + j < field[0].length)
                        && (field[x + i][y + j]))
                    counter++;
            }
        }
        return counter;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        State state = (State) obj;
        if ((field.length != state.field.length) || (field[0].length != state.field[0].length))
            return false;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] != state.field[i][j]) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(field);
    }
}