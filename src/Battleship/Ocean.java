package Battleship;

public class Ocean {
    final public int length;
    final public int width;
    Cell[][] field;
    /** Constructor of the ocean */
    public Ocean(int enter_length, int enter_width) {
        this.length = enter_length;
        this.width = enter_width;
        field = new Cell [enter_length + 2][enter_width + 2];
        for (int i = 0; i < enter_length + 2; ++i) {
            for (int j = 0; j < enter_width + 2; ++j) {
                field[i][j] = new Cell();
            }
        }
    }

    /** Method where we put the ocean on the screen */
    public void Write() {
        System.out.print("     ");
        for (int i = 1; i < this.length + 1; ++i) {
            if (i < 9) {
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        for (int i = 1; i < this.length + 1; ++i) {
            if (i < 10) {
                System.out.print(i + "   ");
            } else {
                System.out.print(i + "  ");
            }
            for (int j = 1; j < this.width + 1; ++j) {
                field[i][j].Write();
            }
            System.out.println();
        }
    }
}
